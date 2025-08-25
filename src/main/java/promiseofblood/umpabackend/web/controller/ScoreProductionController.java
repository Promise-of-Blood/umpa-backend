package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.ServiceBoardService;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.request.ScoreProductionServicePostCreateRequest;
import promiseofblood.umpabackend.web.schema.response.ScoreProductionServicePostDetailResponse;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ScoreProductionController {

  private final ServiceBoardService serviceBoardService;

  // ***************
  // * 악보제작 서비스 *
  // ***************
  @Tag(name = "서비스 관리 API(악보 제작)")
  @PostMapping(value = "/score-production", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ScoreProductionServicePostDetailResponse> registerScoreProduction(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute ScoreProductionServicePostCreateRequest scoreProductionRequest) {

    String loginId = securityUserDetails.getUsername();
    ScoreProductionServicePostDetailResponse scoreProductionServicePostResponse = serviceBoardService.createScoreProductionServicePost(
      loginId, scoreProductionRequest);

    return ResponseEntity.ok(scoreProductionServicePostResponse);
  }

  @Tag(name = "서비스 관리 API(악보 제작)")
  @GetMapping(path = "/score-production/{id}")
  public ResponseEntity<ScoreProductionServicePostDetailResponse> getScoreProductionServicePost(
    @PathVariable Long id) {

    ScoreProductionServicePostDetailResponse scoreProductionResponse =
      serviceBoardService.getScoreProductionServicePost(id);

    return ResponseEntity.ok(scoreProductionResponse);
  }

}
