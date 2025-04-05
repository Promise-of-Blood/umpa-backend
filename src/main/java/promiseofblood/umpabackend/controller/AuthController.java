package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.response.Oauth2LoginUrlResponse;
import promiseofblood.umpabackend.dto.response.Oauth2RegisterResponse;
import promiseofblood.umpabackend.dto.response.Oauth2TokenResponse;
import promiseofblood.umpabackend.domain.service.Oauth2Service;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "인증 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final Oauth2Service oauth2Service;

  @Operation(summary = "OAuth2 회원가입")
  @PostMapping("/register/oauth2/")
  public ResponseEntity<Oauth2RegisterResponse> registerWithNaver(
    @RequestBody Oauth2RegisterRequest oauth2RegisterRequest
  ) {
    Oauth2RegisterResponse oauth2RegisterResponse = oauth2Service.register(oauth2RegisterRequest);

    return ResponseEntity.ok(oauth2RegisterResponse);
  }

  @Operation(summary = "OAuth2 flow 액세스 토큰 발급")
  @GetMapping("/register/oauth2/{oauth2ProviderName}/access-token")
  public ResponseEntity<Oauth2TokenResponse> getAccessToken(
    @PathVariable String oauth2ProviderName, @RequestParam String code) {

    return ResponseEntity.ok(oauth2Service.getOauth2AccessToken(oauth2ProviderName, code));
  }

  @Operation(summary = "OAuth2 flow 웹 로그인 url 조회")
  @GetMapping("/register/oauth2/{oauth2ProviderName}/url")
  public ResponseEntity<Oauth2LoginUrlResponse> getNaverUrl(
    @PathVariable String oauth2ProviderName) {

    return ResponseEntity.ok(oauth2Service.getLoginUrl(oauth2ProviderName));
  }

}
