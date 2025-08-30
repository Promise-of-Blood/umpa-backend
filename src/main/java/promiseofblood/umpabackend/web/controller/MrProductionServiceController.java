package promiseofblood.umpabackend.web.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
import promiseofblood.umpabackend.application.command.CreateMrProductionServicePostCommand;
import promiseofblood.umpabackend.application.query.RetrieveMrServicePostQuery;
import promiseofblood.umpabackend.application.service.MrProductionService;
import promiseofblood.umpabackend.application.service.ReviewService;
import promiseofblood.umpabackend.application.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.ServicePostDto.ServicePostResponse;
import promiseofblood.umpabackend.dto.ServiceReviewDto;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.response.ApiResponse.PaginatedResponse;
import promiseofblood.umpabackend.web.schema.request.CreateMrProductionServicePostRequest;
import promiseofblood.umpabackend.web.schema.response.RetrieveMrProductionServicePostResponse;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class MrProductionServiceController {

  private final MrProductionService mrProductionService;
  private final ServiceBoardService serviceBoardService;
  private final ReviewService reviewService;


  @Tag(name = "서비스 관리 API(MR제작)")
  @GetMapping(path = "/mr-production")
  public ResponseEntity<PaginatedResponse<ServicePostResponse>>
  getAllMrProductionServices(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") @Min(value = 1, message = "size는 0보다 커야 합니다.") int size) {

    Page<ServicePostResponse> servicePostResponsePage =
      this.serviceBoardService.getAllServices("MR_PRODUCTION", page, size);

    return ResponseEntity.ok(PaginatedResponse.from(servicePostResponsePage));
  }

  @Tag(name = "서비스 관리 API(MR제작)")
  @PostMapping(path = "/mr-production", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<RetrieveMrProductionServicePostResponse> registerMrProduction(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute CreateMrProductionServicePostRequest createMrProductionServicePostRequest) {

    String loginId = securityUserDetails.getUsername();

    CreateMrProductionServicePostCommand command = CreateMrProductionServicePostCommand.builder()
      .loginId(loginId)
      .title(createMrProductionServicePostRequest.getTitle())
      .thumbnailImage(createMrProductionServicePostRequest.getThumbnailImage())
      .description(createMrProductionServicePostRequest.getDescription())
      .serviceCostValue(createMrProductionServicePostRequest.getCost())
      .serviceCostUnit("곡")
      .additionalCostPolicy(createMrProductionServicePostRequest.getAdditionalCostPolicy())
      .freeRevisionCount(createMrProductionServicePostRequest.getFreeRevisionCount())
      .additionalRevisionCost(createMrProductionServicePostRequest.getAdditionalRevisionCost())
      .averageDuration(createMrProductionServicePostRequest.getAverageDuration())
      .usingSoftwareList(createMrProductionServicePostRequest.getSoftwareList())
      .sampleMrUrls(createMrProductionServicePostRequest.getSampleMrUrls())
      .build();
    RetrieveMrProductionServicePostResponse response = mrProductionService.createMrProductionServicePost(
      command);

    return ResponseEntity.ok(response);
  }

  @Tag(name = "서비스 관리 API(MR제작)")
  @GetMapping(path = "/mr-production/{id}")
  public ResponseEntity<RetrieveMrProductionServicePostResponse> getMrProductionServicePost(
    @PathVariable Long id) {

    RetrieveMrServicePostQuery query = new RetrieveMrServicePostQuery(id);
    var response = mrProductionService.retrieveMrProductionServicePost(query);

    return ResponseEntity.ok(response);
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
