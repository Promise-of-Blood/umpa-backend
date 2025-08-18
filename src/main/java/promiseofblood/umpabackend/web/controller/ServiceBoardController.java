package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.ReviewService;
import promiseofblood.umpabackend.application.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto;
import promiseofblood.umpabackend.dto.ServicePostDto;
import promiseofblood.umpabackend.dto.ServiceReviewDto;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.ApiResponse.PaginatedResponse;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceBoardController {

  private final ServiceBoardService serviceBoardService;
  private final ReviewService reviewService;

  // lesson
  @Tag(name = "서비스 관리 API(레슨)")
  @PostMapping("/lesson")
  @PreAuthorize("isAuthenticated()")
  public void registerLesson() {
  }

  // accompaniment
  @Tag(name = "서비스 관리 API(합주)")
  @PostMapping(path = "/accompaniment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<AccompanimentServicePostDto.AccompanimentServicePostResponse>
  registerAccompaniment(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute
    AccompanimentServicePostDto.AccompanimentPostRequest accompanimentPostRequest) {
    String loginId = securityUserDetails.getUsername();

    AccompanimentServicePostDto.AccompanimentServicePostResponse accompanimentPostResponse =
      serviceBoardService.createAccompanimentServicePost(loginId, accompanimentPostRequest);

    return ResponseEntity.ok(accompanimentPostResponse);
  }

  // score-production
  @Tag(name = "서비스 관리 API(악보 제작)")
  @PostMapping("/score-production")
  @PreAuthorize("isAuthenticated()")
  public void registerScoreProduction() {
  }

  // mr-production
  @Tag(name = "서비스 관리 API(MR제작)")
  @GetMapping(path = "/mr-production")
  public ResponseEntity<PaginatedResponse<ServicePostDto.ServicePostResponse>>
  getAllMrProductionServices(
    @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

    Page<ServicePostDto.ServicePostResponse> servicePostResponsePage =
      this.serviceBoardService.getAllServices("MR_PRODUCTION", page, size);

    return ResponseEntity.ok(PaginatedResponse.from(servicePostResponsePage));
  }

  @Tag(name = "서비스 관리 API(MR제작)")
  @PostMapping(path = "/mr-production", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<MrProductionServicePostDto.MrProductionResponse> registerMrProduction(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute MrProductionServicePostDto.MrProductionPostRequest mrProductionPostRequest) {

    String loginId = securityUserDetails.getUsername();

    MrProductionServicePostDto.MrProductionResponse mrProductionResponse =
      serviceBoardService.createMrProductionServicePost(loginId, mrProductionPostRequest);

    return ResponseEntity.ok(mrProductionResponse);
  }

  @Tag(name = "서비스 관리 API(MR제작)")
  @GetMapping(path = "/mr-production/{id}")
  public ResponseEntity<MrProductionServicePostDto.MrProductionResponse> getMrProductionServicePost(
    @PathVariable Long id) {
    MrProductionServicePostDto.MrProductionResponse mrProductionResponse =
      serviceBoardService.getMrProductionServicePost(id);

    return ResponseEntity.ok(mrProductionResponse);
  }

  @Tag(name = "서비스 관리 API(MR제작)")
  @PostMapping(path = "/mr-production/{id}/reviews")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ServiceReviewDto.ReviewResponse> createMrProductionReview(
    @PathVariable Long id,
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @Valid @RequestBody ServiceReviewDto.ReviewRequest reviewRequest) {

    ServiceReviewDto.ReviewResponse reviewResponse =
      ServiceReviewDto.ReviewResponse.from(this.reviewService.createReview(id, reviewRequest));

    return ResponseEntity.ok(reviewResponse);
  }
}
