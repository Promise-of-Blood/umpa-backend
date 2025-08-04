package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.core.security.SecurityUserDetails;
import promiseofblood.umpabackend.domain.service.ReviewService;
import promiseofblood.umpabackend.domain.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto.MrProductionServicePostRequest;
import promiseofblood.umpabackend.dto.request.ReviewRequest;
import promiseofblood.umpabackend.dto.response.MrProductionServicePostResponse;
import promiseofblood.umpabackend.dto.response.ReviewResponse;
import promiseofblood.umpabackend.dto.response.ServicePostResponse;
import promiseofblood.umpabackend.dto.response.common.PaginatedResponse;

@RestController
@RequestMapping("/api/v1/services")
@Tag(name = "서비스 관리 API", description = "서비스 등록, 조회, 수정, 삭제 API")
@RequiredArgsConstructor
public class ServiceBoardController {

  private final ServiceBoardService serviceBoardService;
  private final ReviewService reviewService;

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
  @PostMapping(path = "/accompaniment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<AccompanimentServicePostDto.AccompanimentServicePostResponse> registerAccompaniment(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute AccompanimentServicePostDto.AccompanimentPostRequest accompanimentPostRequest
  ) {
    String loginId = securityUserDetails.getUsername();

    AccompanimentServicePostDto.AccompanimentServicePostResponse accompanimentPostResponse =
      serviceBoardService.createAccompanimentServicePost(loginId, accompanimentPostRequest);

    return ResponseEntity.ok(accompanimentPostResponse);
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

  @GetMapping(path = "/mr-production/{id}")
  public ResponseEntity<MrProductionServicePostResponse> getMrProductionServicePost(
    @PathVariable Long id) {
    MrProductionServicePostResponse mrProductionServicePostResponse = serviceBoardService
      .getMrProductionServicePost(id);

    return ResponseEntity.ok(mrProductionServicePostResponse);
  }

  @PostMapping(path = "/mr-production/{id}/reviews")
  public ResponseEntity<ReviewResponse> createMrProductionReview(
    @PathVariable Long id,
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @Valid @RequestBody ReviewRequest reviewRequest
  ) {

    ReviewResponse reviewResponse = ReviewResponse.from(
      this.reviewService.createReview(id, reviewRequest));

    return ResponseEntity.ok(reviewResponse);
  }

}
