package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.ScoreProductionService;
import promiseofblood.umpabackend.application.service.ServiceBoardService;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.request.CreateScoreProductionServicePosRequest;
import promiseofblood.umpabackend.web.schema.response.ApiResponse.PaginatedResponse;
import promiseofblood.umpabackend.web.schema.response.ListScoreProductionServicePostResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveScoreProductionServicePostResponse;

@RestController
@RequiredArgsConstructor
@Tag(name = "서비스 관리 API(악보 제작)")
@RequestMapping("/api/v1/services/score-production")
public class ScoreProductionController {

  private final ServiceBoardService serviceBoardService;
  private final ScoreProductionService scoreProductionService;

  @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<RetrieveScoreProductionServicePostResponse> registerScoreProduction(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @ModelAttribute CreateScoreProductionServicePosRequest scoreProductionRequest) {

    String loginId = securityUserDetails.getUsername();
    RetrieveScoreProductionServicePostResponse scoreProductionServicePostResponse =
        serviceBoardService.createScoreProductionServicePost(loginId, scoreProductionRequest);

    return ResponseEntity.ok(scoreProductionServicePostResponse);
  }

  @GetMapping("")
  public ResponseEntity<PaginatedResponse<ListScoreProductionServicePostResponse>>
      getAllLessonServices(
          @RequestParam(defaultValue = "0") int page,
          @RequestParam(defaultValue = "10") @Min(value = 1, message = "size는 0보다 커야 합니다.")
              int size) {

    Page<ListScoreProductionServicePostResponse> servicePostResponsePage =
        this.scoreProductionService.getAllServices(page, size);

    return ResponseEntity.ok(PaginatedResponse.from(servicePostResponsePage));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<RetrieveScoreProductionServicePostResponse> getScoreProductionServicePost(
      @PathVariable Long id) {

    RetrieveScoreProductionServicePostResponse scoreProductionResponse =
        serviceBoardService.getScoreProductionServicePost(id);

    return ResponseEntity.ok(scoreProductionResponse);
  }
}
