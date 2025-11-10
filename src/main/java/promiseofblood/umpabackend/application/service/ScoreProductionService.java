package promiseofblood.umpabackend.application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.domain.entity.SampleScoreImageUrl;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.ScoreProductionServicePostRepository;
import promiseofblood.umpabackend.domain.repository.ServicePostRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.PostDisplayStatus;
import promiseofblood.umpabackend.domain.vo.PublishStatus;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.web.schema.request.CreateScoreProductionServicePosRequest;
import promiseofblood.umpabackend.web.schema.response.ListScoreProductionServicePostResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveScoreProductionServicePostResponse;

@Service
@RequiredArgsConstructor
public class ScoreProductionService {

  private final StorageService storageService;
  private final UserRepository userRepository;
  private final ServicePostRepository servicePostRepository;
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

  @Transactional
  public RetrieveScoreProductionServicePostResponse createScoreProductionServicePost(
      String loginId, CreateScoreProductionServicePosRequest scoreProductionRequest) {

    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String thumbnailFilePath =
        storageService.store(
            scoreProductionRequest.getThumbnailImage(),
            "service/" + user.getId() + "/score-production");

    List<SampleScoreImageUrl> sampleScoreFilePaths = new ArrayList<>();
    for (var sampleScoreImage : scoreProductionRequest.getSampleScoreImages()) {
      String sampleScoreFilePath =
          storageService.store(
              sampleScoreImage, "service/" + user.getId() + "/score-production/sample-score");
      sampleScoreFilePaths.add(SampleScoreImageUrl.of(sampleScoreFilePath));
    }

    List<ServiceCost> serviceCosts = new ArrayList<>();
    for (String costByScoreType : scoreProductionRequest.getCostByScoreType().split(",")) {
      String[] parts = costByScoreType.split(":");
      String scoreType = parts[0];
      int cost = Integer.parseInt(parts[1]);
      serviceCosts.add(ServiceCost.builder().unit(scoreType).cost(cost).build());
    }

    ScoreProductionServicePost scoreProductionServicePost =
        ScoreProductionServicePost.builder()
            .user(user)
            .title(scoreProductionRequest.getTitle())
            .thumbnailImageUrl(thumbnailFilePath)
            .description(scoreProductionRequest.getDescription())
            .publishStatus(PublishStatus.PUBLISHED)
            .serviceCosts(serviceCosts)
            .additionalCostPolicy(scoreProductionRequest.getAdditionalCostPolicy())
            .freeRevisionCount(scoreProductionRequest.getFreeRevisionCount())
            .additionalRevisionCost(scoreProductionRequest.getAdditionalRevisionCost())
            .averageDuration(DurationRange.of(scoreProductionRequest.getAverageDuration()))
            .usingSoftwareList(scoreProductionRequest.getSoftwareList())
            .sampleScoreImageUrls(sampleScoreFilePaths)
            .build();
    servicePostRepository.save(scoreProductionServicePost);

    return RetrieveScoreProductionServicePostResponse.from(scoreProductionServicePost);
  }

  @Transactional(readOnly = true)
  public RetrieveScoreProductionServicePostResponse getScoreProductionServicePost(Long id) {

    ScoreProductionServicePost scoreProductionServicePost =
        scoreProductionServicePostRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 악보 제작 서비스 게시글이 존재하지 않습니다."));

    return RetrieveScoreProductionServicePostResponse.from(scoreProductionServicePost);
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
