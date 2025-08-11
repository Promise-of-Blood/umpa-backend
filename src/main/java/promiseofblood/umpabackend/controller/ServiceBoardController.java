package promiseofblood.umpabackend.controller;

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
import promiseofblood.umpabackend.core.security.SecurityUserDetails;
import promiseofblood.umpabackend.domain.service.ReviewService;
import promiseofblood.umpabackend.domain.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto;
import promiseofblood.umpabackend.dto.PaginatedResponse;
import promiseofblood.umpabackend.dto.request.ReviewRequest;
import promiseofblood.umpabackend.dto.response.ReviewResponse;
import promiseofblood.umpabackend.dto.response.ServicePostResponse;

@RestController
@RequestMapping("/api/v1/services")
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
  @Tag(name = "서비스 관리 API(레슨)")
  @PostMapping("/lesson")
  @PreAuthorize("isAuthenticated()")
  public void registerLesson() {

  }

  // accompaniment
  @Tag(name = "서비스 관리 API(합주)")
  @PostMapping(path = "/accompaniment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
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
  @Tag(name = "서비스 관리 API(악보 제작)")
  @PostMapping("/score-production")
  @PreAuthorize("isAuthenticated()")
  public void registerScoreProduction() {

  }

  // mr-production
  @Tag(name = "서비스 관리 API(MR제작)")
  @PostMapping(path = "/mr-production", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<MrProductionServicePostDto.MrProductionServicePostResponse> registerMrProduction(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute MrProductionServicePostDto.MrProductionServicePostRequest mrProductionServicePostRequest
  ) {

    String loginId = securityUserDetails.getUsername();

    MrProductionServicePostDto.MrProductionServicePostResponse mrProductionServicePostResponse = serviceBoardService
      .createMrProductionServicePost(loginId, mrProductionServicePostRequest);

    return ResponseEntity.ok(mrProductionServicePostResponse);
  }

  @Tag(name = "서비스 관리 API(MR제작)")
  @GetMapping(path = "/mr-production/{id}")
  public ResponseEntity<MrProductionServicePostDto.MrProductionServicePostResponse> getMrProductionServicePost(
    @PathVariable Long id) {
    MrProductionServicePostDto.MrProductionServicePostResponse mrProductionServicePostResponse = serviceBoardService
      .getMrProductionServicePost(id);

    return ResponseEntity.ok(mrProductionServicePostResponse);
  }

  @Tag(name = "서비스 관리 API(MR제작)")
  @PostMapping(path = "/mr-production/{id}/reviews")
  @PreAuthorize("isAuthenticated()")
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
