package promiseofblood.umpabackend.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.domain.entity.DurationRange;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.ServiceCost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.dto.request.MrProductionServicePostRequest;
import promiseofblood.umpabackend.dto.response.MrProductionServicePostResponse;
import promiseofblood.umpabackend.repository.ServiceRegistrationRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ServiceBoardService {

  private final StorageService storageService;
  private final UserRepository userRepository;
  private final ServiceRegistrationRepository serviceRegistrationRepository;

  public MrProductionServicePostResponse createMrProductionServicePost(
    String loginId,
    MrProductionServicePostRequest mrProductionServicePostRequest) {

    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath = storageService.store(
      mrProductionServicePostRequest.getThumbnailImage(),
      "service/" + user.getId() + "/mr-production"
    );

    MrProductionServicePost mrProductionRegistration = MrProductionServicePost.builder()
      .userId(user.getId())
      .title(mrProductionServicePostRequest.getTitle())
      .thumbnailImageUrl(filePath)
      .description(mrProductionServicePostRequest.getDescription())
      .serviceCost(
        ServiceCost.builder()
          .cost(mrProductionServicePostRequest.getCost())
          .unit("곡")
          .build()
      )
      .additionalCostPolicy(mrProductionServicePostRequest.getAdditionalCostPolicy())
      .freeRevisionCount(mrProductionServicePostRequest.getFreeRevisionCount())
      .averageDuration(DurationRange.of(mrProductionServicePostRequest.getAverageDuration()))
      .softwareUsed(mrProductionServicePostRequest.getSoftwareUsed())
      .sampleMrUrls(mrProductionServicePostRequest.getSampleMrUrls())
      .build();
    serviceRegistrationRepository.save(mrProductionRegistration);

    return MrProductionServicePostResponse.of(mrProductionRegistration);
  }

}
