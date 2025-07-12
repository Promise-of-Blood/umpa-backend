package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.core.security.SecurityUserDetails;
import promiseofblood.umpabackend.domain.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.request.MrProductionServicePostRequest;
import promiseofblood.umpabackend.dto.response.MrProductionServicePostResponse;
import promiseofblood.umpabackend.dto.response.ServicePostResponse;
import promiseofblood.umpabackend.dto.response.common.PaginatedResponse;

@RestController
@RequestMapping("/api/v1/services")
@Tag(name = "서비스 관리 API", description = "서비스 등록, 조회, 수정, 삭제 API")
@RequiredArgsConstructor
public class ServiceBoardController {

  private final ServiceBoardService serviceBoardService;

  @GetMapping("")
  public ResponseEntity<PaginatedResponse<ServicePostResponse>> getAllServices(
    @RequestParam(required = false) String serviceType,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {

    Page<ServicePostResponse> servicePostResponsePage = this.serviceBoardService.getAllServices(
      serviceType, page, size
    );

    return ResponseEntity.ok(
      PaginatedResponse.from(servicePostResponsePage)
    );
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
