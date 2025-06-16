package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promiseofblood.umpabackend.domain.service.Oauth2Service;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.request.TokenRefreshRequest;
import promiseofblood.umpabackend.dto.response.JwtResponse;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;


@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class Oauth2Controller {

  private final Oauth2Service oauth2Service;

  @GetMapping("/urls")
  public Map<String, String> getAuthorizationUrls() {

    return oauth2Service.generateAuthorizationUrls();
  }

  @PostMapping(value = "/{providerName}/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RegisterCompleteResponse> registerOauth2User(
    @PathVariable String providerName,
    @Parameter(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE))
    Oauth2RegisterRequest oauth2RegisterRequest
  ) {

    RegisterCompleteResponse registerCompleteResponse = oauth2Service.registerOauth2User(
      providerName, oauth2RegisterRequest);

    return ResponseEntity.ok(registerCompleteResponse);
  }


  @GetMapping("/{providerName}/callback")
  public Oauth2ProfileResponse oauth2AuthorizationCallback(
    @PathVariable String providerName,
    String code
  ) {

    return oauth2Service.getOauth2Profile(providerName, code);
  }

  @PostMapping("/token/refresh")
  public JwtResponse refreshToken(@RequestBody TokenRefreshRequest request) {

    return oauth2Service.refreshToken(request.getRefreshToken());
  }
}
