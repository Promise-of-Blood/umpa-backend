package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.core.security.SecurityUserDetails;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.request.MrProductionServicePostRequest;
import promiseofblood.umpabackend.dto.response.MrProductionServicePostResponse;
import promiseofblood.umpabackend.dto.response.ServiceResponse;
import promiseofblood.umpabackend.repository.ServiceRegistrationRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/services")
@Tag(name = "서비스 관리 API", description = "서비스 등록, 조회, 수정, 삭제 API")
@RequiredArgsConstructor
public class ServiceController {

  private final ServiceBoardService serviceBoardService;
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
  @PostMapping(path = "/mr-production", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<MrProductionServicePostResponse> registerMrProduction(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute MrProductionServicePostRequest mrProductionServicePostRequest
  ) {
    String loginId = securityUserDetails.getUsername();

    MrProductionServicePostResponse mrProductionServicePostResponse = serviceBoardService
      .createMrProductionServicePost(loginId, mrProductionServicePostRequest);

    return ResponseEntity.ok(mrProductionServicePostResponse);
  }


}
