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
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto;
import promiseofblood.umpabackend.dto.ScoreProductionServicePostDto;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ServiceBoardController {

  private final ServiceBoardService serviceBoardService;

  // ************
  // * 합주 서비스 *
  // ************
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

  // ***************
  // * 악보제작 서비스 *
  // ***************
  @Tag(name = "서비스 관리 API(악보 제작)")
  @PostMapping(value = "/score-production", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ScoreProductionServicePostDto.ScoreProductionServicePostResponse> registerScoreProduction(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute ScoreProductionServicePostDto.ScoreProductionServicePosRequest scoreProductionRequest) {

    String loginId = securityUserDetails.getUsername();
    ScoreProductionServicePostDto.ScoreProductionServicePostResponse scoreProductionServicePostResponse = serviceBoardService.createScoreProductionServicePost(
      loginId, scoreProductionRequest);

    return ResponseEntity.ok(scoreProductionServicePostResponse);
  }

  @Tag(name = "서비스 관리 API(악보 제작)")
  @GetMapping(path = "/score-production/{id}")
  public ResponseEntity<ScoreProductionServicePostDto.ScoreProductionServicePostResponse> getScoreProductionServicePost(
    @PathVariable Long id) {

    ScoreProductionServicePostDto.ScoreProductionServicePostResponse scoreProductionResponse =
      serviceBoardService.getScoreProductionServicePost(id);

    return ResponseEntity.ok(scoreProductionResponse);
  }


}
