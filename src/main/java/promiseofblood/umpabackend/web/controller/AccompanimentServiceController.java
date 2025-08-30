package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto;
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto.AccompanimentServicePostResponse;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class AccompanimentServiceController {

  private final ServiceBoardService serviceBoardService;

  // ************
  // * 합주 서비스 *
  // ************
  @Tag(name = "서비스 관리 API(합주)")
  @PostMapping(path = "/accompaniment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<AccompanimentServicePostResponse>
  registerAccompaniment(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute @Valid
    AccompanimentServicePostDto.AccompanimentPostRequest accompanimentPostRequest) {
    String loginId = securityUserDetails.getUsername();

    AccompanimentServicePostDto.AccompanimentServicePostResponse accompanimentPostResponse =
      serviceBoardService.createAccompanimentServicePost(loginId, accompanimentPostRequest);

    return ResponseEntity.ok(accompanimentPostResponse);
  }

}
