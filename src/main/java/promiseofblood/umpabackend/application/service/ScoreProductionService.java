package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.repository.ScoreProductionServicePostRepository;
import promiseofblood.umpabackend.web.schema.response.ListScoreProductionServicePostResponse;

@Service
@RequiredArgsConstructor
public class ScoreProductionService {

  private final ScoreProductionServicePostRepository scoreProductionServicePostRepository;

  @Transactional(readOnly = true)
  public Page<ListScoreProductionServicePostResponse> getAllServices(int page, int size) {

    Page<ScoreProductionServicePost> servicePostPage =
        scoreProductionServicePostRepository.findAll(PageRequest.of(page, size));

    return servicePostPage.map(ListScoreProductionServicePostResponse::from);
  }
}
