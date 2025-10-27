package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.command.CreateMrProductionServicePostCommand;
import promiseofblood.umpabackend.application.exception.UnauthorizedException;
import promiseofblood.umpabackend.application.query.RetrieveMrServicePostQuery;
import promiseofblood.umpabackend.application.service.MrProductionService;
import promiseofblood.umpabackend.application.service.ServicePostLikeService;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.filtertype.ServiceFilter;
import promiseofblood.umpabackend.web.schema.request.CreateMrProductionServicePostRequest;
import promiseofblood.umpabackend.web.schema.response.ApiResponse.PaginatedResponse;
import promiseofblood.umpabackend.web.schema.response.ListMrProductionServicePostResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveMrProductionServicePostResponse;

@RestController
@Tag(name = "서비스 관리 API(MR제작)")
@RequestMapping("/api/v1/services/mr-production")
@RequiredArgsConstructor
public class MrProductionServiceController {

  private final MrProductionService mrProductionService;
  private final ServicePostLikeService servicePostLikeService;

  @GetMapping(path = "")
  public ResponseEntity<PaginatedResponse<ListMrProductionServicePostResponse>>
      getAllMrProductionServices(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") @Min(value = 1, message = "size는 0보다 커야 합니다.")
              int size,
          @RequestParam(defaultValue = "ALL") ServiceFilter filter,
          @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    if (filter == ServiceFilter.LIKED && securityUserDetails == null) {
      throw new UnauthorizedException("좋아요 필터는 로그인이 필요합니다.");
    }

    Page<ListMrProductionServicePostResponse> servicePostResponsePage =
        switch (filter) {
          case LIKED ->
              this.mrProductionService.getLikedServices(
                  securityUserDetails.getUsername(), page, size);
          default -> this.mrProductionService.getAllServices(page, size);
        };

    return ResponseEntity.ok(PaginatedResponse.from(servicePostResponsePage));
  }

  @PostMapping(path = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<RetrieveMrProductionServicePostResponse> registerMrProduction(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @ModelAttribute CreateMrProductionServicePostRequest createMrProductionServicePostRequest) {

    String loginId = securityUserDetails.getUsername();

    CreateMrProductionServicePostCommand command =
        CreateMrProductionServicePostCommand.builder()
            .loginId(loginId)
            .title(createMrProductionServicePostRequest.getTitle())
            .thumbnailImage(createMrProductionServicePostRequest.getThumbnailImage())
            .description(createMrProductionServicePostRequest.getDescription())
            .serviceCostValue(createMrProductionServicePostRequest.getCost())
            .serviceCostUnit("곡")
            .additionalCostPolicy(createMrProductionServicePostRequest.getAdditionalCostPolicy())
            .freeRevisionCount(createMrProductionServicePostRequest.getFreeRevisionCount())
            .additionalRevisionCost(
                createMrProductionServicePostRequest.getAdditionalRevisionCost())
            .averageDuration(createMrProductionServicePostRequest.getAverageDuration())
            .usingSoftwareList(createMrProductionServicePostRequest.getSoftwareList())
            .sampleMrUrls(createMrProductionServicePostRequest.getSampleMrUrls())
            .build();
    RetrieveMrProductionServicePostResponse response =
        mrProductionService.createMrProductionServicePost(command);

    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<RetrieveMrProductionServicePostResponse> getMrProductionServicePost(
      @PathVariable Long id) {

    RetrieveMrServicePostQuery query = new RetrieveMrServicePostQuery(id);
    var response = mrProductionService.retrieveMrProductionServicePost(query);

    return ResponseEntity.ok(response);
  }

  @PreAuthorize("isAuthenticated()")
  @PostMapping(path = "/{id}/likes")
  public ResponseEntity<Void> addLike(
      @PathVariable Long id, @AuthenticationPrincipal SecurityUserDetails userDetails) {

    servicePostLikeService.likeServicePost(userDetails.getUsername(), id);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PreAuthorize("isAuthenticated()")
  @DeleteMapping(path = "/{id}/likes")
  public ResponseEntity<Void> unlikeServicePost(
      @AuthenticationPrincipal SecurityUserDetails userDetails, @PathVariable Long id) {

    servicePostLikeService.unlikeServicePost(userDetails.getUsername(), id);
    return ResponseEntity.noContent().build();
  }
}
