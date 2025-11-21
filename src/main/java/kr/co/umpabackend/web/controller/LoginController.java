package kr.co.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import kr.co.umpabackend.application.service.Oauth2Service;
import kr.co.umpabackend.application.service.UserService;
import kr.co.umpabackend.infrastructure.oauth.dto.Oauth2ProfileResponse;
import kr.co.umpabackend.web.schema.request.LoginByLoginIdPasswordRequest;
import kr.co.umpabackend.web.schema.request.LoginByOauth2Request;
import kr.co.umpabackend.web.schema.request.RefreshJwtRequest;
import kr.co.umpabackend.web.schema.response.LoginCompleteResponse;
import kr.co.umpabackend.web.schema.response.RetrieveOauth2ProviderInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "토큰 발급 API")
public class LoginController {

  private final UserService userService;
  private final Oauth2Service oauth2Service;

  @PostMapping("token/{providerName}")
  public ResponseEntity<LoginCompleteResponse> createOauth2Token(
      @PathVariable String providerName, @RequestBody LoginByOauth2Request oauth2LoginRequest) {

    LoginCompleteResponse loginCompleteResponse =
        oauth2Service.generateOauth2Jwt(
            providerName,
            oauth2LoginRequest.getExternalIdToken(),
            oauth2LoginRequest.getExternalAccessToken());

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @PostMapping("/token/general")
  public ResponseEntity<LoginCompleteResponse> createToken(
      @RequestBody LoginByLoginIdPasswordRequest request) {

    LoginCompleteResponse loginCompleteResponse =
        userService.loginIdPasswordJwtLogin(request.getLoginId(), request.getPassword());

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<LoginCompleteResponse> refreshToken(
      @RequestBody RefreshJwtRequest request) {

    return ResponseEntity.ok(userService.refreshToken(request.getRefreshToken()));
  }

  @GetMapping("/oauth2-authorization-urls")
  public ResponseEntity<Map<String, RetrieveOauth2ProviderInfoResponse>> getAuthorizationUrls() {

    return ResponseEntity.ok(oauth2Service.generateAuthorizationUrls());
  }

  @GetMapping("/callback/{providerName}")
  @Hidden
  public ResponseEntity<Oauth2ProfileResponse> oauth2AuthorizationCallback(
      @PathVariable String providerName, String code) {

    return ResponseEntity.ok(oauth2Service.getOauth2Profile(providerName, code));
  }
}
