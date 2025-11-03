package promiseofblood.umpabackend.application.service;

import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.repository.ScoreProductionServicePostRepository;
import promiseofblood.umpabackend.domain.vo.PostDisplayStatus;
import promiseofblood.umpabackend.web.schema.response.ListScoreProductionServicePostResponse;

@Service
@RequiredArgsConstructor
public class ScoreProductionService {

  private final ScoreProductionServicePostRepository scoreProductionServicePostRepository;

  @Transactional(readOnly = true)
  public Page<ListScoreProductionServicePostResponse> getAllServices(int page, int size) {

    List<ScoreProductionServicePost> allPosts =
        scoreProductionServicePostRepository.findAll().stream()
            .filter(post -> post.getDeletedAt() == null)
            .sorted(getServicePostComparator())
            .toList();

    int start = page * size;
    int end = Math.min(start + size, allPosts.size());

    if (start >= allPosts.size()) {
      return new PageImpl<>(List.of(), PageRequest.of(page, size), allPosts.size());
    }

    List<ListScoreProductionServicePostResponse> content =
        allPosts.subList(start, end).stream()
            .map(ListScoreProductionServicePostResponse::from)
            .toList();

    return new PageImpl<>(content, PageRequest.of(page, size), allPosts.size());
  }

  /** 게시물 정렬 Comparator: PUBLISHED -> PAUSED -> OWNER_MISSING 순, 같은 상태 내에서는 최신순 */
  private Comparator<ScoreProductionServicePost> getServicePostComparator() {
    return Comparator.comparing(ScoreProductionServicePost::getDisplayStatus, this::compareStatus)
        .thenComparing(ScoreProductionServicePost::getCreatedAt, Comparator.reverseOrder());
  }

  private int compareStatus(PostDisplayStatus s1, PostDisplayStatus s2) {
    int priority1 = getStatusPriority(s1);
    int priority2 = getStatusPriority(s2);
    return Integer.compare(priority1, priority2);
  }

  private int getStatusPriority(PostDisplayStatus status) {
    return switch (status) {
      case PUBLISHED -> 1;
      case PAUSED -> 2;
      case OWNER_MISSING -> 3;
      case DELETED -> 4;
    };
  }
}
