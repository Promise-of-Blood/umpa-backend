package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.core.security.SecurityUserDetails;
import promiseofblood.umpabackend.domain.entity.DurationRange;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.ServiceCost;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.dto.request.MrProductionRegisterRequest;
import promiseofblood.umpabackend.dto.response.MrProductionServicePostResponse;
import promiseofblood.umpabackend.dto.response.ServiceResponse;
import promiseofblood.umpabackend.repository.ServiceRegistrationRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/services")
@Tag(name = "서비스 관리 API", description = "서비스 등록, 조회, 수정, 삭제 API")
@RequiredArgsConstructor
public class ServiceController {

  private final UserRepository userRepository;
  private final ServiceRegistrationRepository serviceRegistrationRepository;

  // all services list
  @GetMapping("")
  public List<ServiceResponse> getAllServices() {

    List<ServicePost> servicePosts = serviceRegistrationRepository.findAll();

    return servicePosts.stream()
      .map(service -> ServiceResponse.builder()
        .id(service.getId())
        .title(service.getTitle())
        .cost(100)
        .unit("곡")
        .reviewRating(4.5f)
        .build())
      .toList();

  }

  // lesson
  @PostMapping("/lesson")
  public void registerLesson() {

  }

  // accompaniment
  @PostMapping("/accompaniment")
  public void registerAccompaniment() {

  }

  // score-production
  @PostMapping("/score-production")
  public void registerScoreProduction() {

  }

  // mr-production
  @PostMapping("/mr-production")
  public ResponseEntity<MrProductionServicePostResponse> registerMrProduction(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody MrProductionRegisterRequest mrProductionRegisterRequest
  ) {

    String loginId = securityUserDetails.getUsername();
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    MrProductionServicePost mrProductionRegistration = MrProductionServicePost.builder()
      .userId(user.getId())
      .title(mrProductionRegisterRequest.getTitle())
      .description(mrProductionRegisterRequest.getDescription())
      .serviceCost(
        ServiceCost.builder()
          .cost(mrProductionRegisterRequest.getCost())
          .unit("곡")
          .build()
      )
      .additionalCostPolicy(mrProductionRegisterRequest.getAdditionalCostPolicy())
      .freeRevisionCount(mrProductionRegisterRequest.getFreeRevisionCount())
      .averageDuration(DurationRange.builder()
        .minValue(mrProductionRegisterRequest.getMinDurationValue())
        .minUnit(mrProductionRegisterRequest.getMinDurationUnit())
        .maxValue(mrProductionRegisterRequest.getMaxDurationValue())
        .maxUnit(mrProductionRegisterRequest.getMaxDurationUnit())
        .build()
      )
      .softwareUsed(mrProductionRegisterRequest.getSoftwareUsed())
      .sampleMrUrls(mrProductionRegisterRequest.getSampleMrUrls())
      .build();

    serviceRegistrationRepository.save(mrProductionRegistration);
    System.out.println("MR Production Service Registered: " + mrProductionRegistration);

    return ResponseEntity.ok(MrProductionServicePostResponse.of(mrProductionRegistration));
  }


}
