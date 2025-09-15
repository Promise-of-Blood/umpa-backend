package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.application.query.RetrieveAccompanimentServicePostQuery;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.repository.AccompanimentServicePostRepository;
import promiseofblood.umpabackend.web.schema.response.RetrieveAccompanimentServicePostResponse;

@Service
@RequiredArgsConstructor
public class AccompanimentService {

  private final AccompanimentServicePostRepository accompanimentServicePostRepository;

  @Transactional(readOnly = true)
  public RetrieveAccompanimentServicePostResponse retrieveAccompanimentServicePost(
      RetrieveAccompanimentServicePostQuery query) {

    // TODO: 이 더러운 코드를 리팩토링 하자
    // 먼저 practiceLocations와 user 정보를 로드
    AccompanimentServicePost post =
        accompanimentServicePostRepository
            .findByIdWithPracticeLocations(query.id())
            .orElseThrow(() -> new ResourceNotFoundException("합주 서비스 서비스 게시글을 찾을 수 없습니다."));

    // 동일한 트랜잭션 내에서 videoUrls 정보를 로드
    // Hibernate는 동일한 ID를 가진 엔티티를 영속성 컨텍스트에서 관리하므로
    // 이 호출은 이미 로드된 엔티티에 videoUrls 정보를 추가로 로드함
    accompanimentServicePostRepository.findByIdWithVideoUrls(query.id());

    return RetrieveAccompanimentServicePostResponse.from(post);
  }
}
