package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.application.query.RetrieveMrServicePostQuery;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.MrProductionServicePostRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto.MrProductionPostRequest;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto.MrProductionResponse;

@Service
@RequiredArgsConstructor
public class MrProductionService {

  private final MrProductionServicePostRepository mrProductionServicePostRepository;
  private final UserRepository userRepository;
  private final StorageService storageService;

  @Transactional
  public MrProductionResponse createMrProductionServicePost(
    String loginId, MrProductionPostRequest mrProductionPostRequest) {

    User user = userRepository
      .findByLoginId(loginId)
      .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

    String filePath = storageService.store(
      mrProductionPostRequest.getThumbnailImage(),
      "service/" + user.getId() + "/mr-production");

    MrProductionServicePost mrProductionServicePost =
      MrProductionServicePost.builder()
        .user(user)
        .title(mrProductionPostRequest.getTitle())
        .thumbnailImageUrl(filePath)
        .description(mrProductionPostRequest.getDescription())
        .serviceCost(
          ServiceCost.builder().cost(mrProductionPostRequest.getCost()).unit("곡").build())
        .additionalCostPolicy(mrProductionPostRequest.getAdditionalCostPolicy())
        .freeRevisionCount(mrProductionPostRequest.getFreeRevisionCount())
        .additionalRevisionCost(mrProductionPostRequest.getAdditionalRevisionCost())
        .averageDuration(DurationRange.of(mrProductionPostRequest.getAverageDuration()))
        .usingSoftwareList(mrProductionPostRequest.getSoftwareList())
        .sampleMrUrls(
          mrProductionPostRequest.getSampleMrUrls().stream().map(SampleMrUrl::of).toList())
        .build();
    mrProductionServicePostRepository.save(mrProductionServicePost);

    return MrProductionResponse.of(mrProductionServicePost);
  }

  @Transactional(readOnly = true)
  public MrProductionResponse retrieveMrProductionServicePost(RetrieveMrServicePostQuery query) {

    MrProductionServicePost mrProductionServicePost = mrProductionServicePostRepository.findById(
        query.id())
      .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 MR 제작 서비스 게시글이 존재하지 않습니다."));

    return MrProductionResponse.of(mrProductionServicePost);
  }
}
