package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.query.RetrieveMrServicePostQuery;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.repository.ServicePostRepository;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto.MrProductionResponse;

@Service
@RequiredArgsConstructor
public class MrProductionService {

  private final ServicePostRepository servicePostRepository;

  @Transactional(readOnly = true)
  public MrProductionResponse retrieveMrProductionServicePost(RetrieveMrServicePostQuery query) {

    var mrProductionServicePost = (MrProductionServicePost) servicePostRepository.findById(
      query.id()).get(); // TODO: 예외처리

    return MrProductionResponse.of(mrProductionServicePost);
  }
}
