package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.application.query.RetrieveMrServicePostQuery;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.repository.MrProductionServicePostRepository;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto.MrProductionResponse;

@Service
@RequiredArgsConstructor
public class MrProductionService {

  private final MrProductionServicePostRepository mrProductionServicePostRepository;

  @Transactional(readOnly = true)
  public MrProductionResponse retrieveMrProductionServicePost(RetrieveMrServicePostQuery query) {

    MrProductionServicePost mrProductionServicePost = mrProductionServicePostRepository.findById(
        query.id())
      .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 MR 제작 서비스 게시글이 존재하지 않습니다."));

    return MrProductionResponse.of(mrProductionServicePost);
  }
}
