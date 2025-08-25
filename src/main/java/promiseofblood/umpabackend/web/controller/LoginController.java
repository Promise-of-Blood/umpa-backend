package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.Oauth2Service;
import promiseofblood.umpabackend.application.service.UserService;
import promiseofblood.umpabackend.dto.LoginDto;
import promiseofblood.umpabackend.dto.LoginDto.LoginCompleteResponse;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "토큰 발급 API")
public class LoginController {

  private final UserService userService;
  private final Oauth2Service oauth2Service;

  @PostMapping("token/{providerName}")
  public ResponseEntity<LoginCompleteResponse> createOauth2Token(
    @PathVariable String providerName,
    @RequestBody LoginDto.Oauth2LoginRequest oauth2LoginRequest) {

    LoginDto.LoginCompleteResponse loginCompleteResponse =
      oauth2Service.generateOauth2Jwt(
        providerName,
        oauth2LoginRequest.getExternalIdToken(),
        oauth2LoginRequest.getExternalAccessToken());

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @PostMapping("/token/general")
  public ResponseEntity<LoginDto.LoginCompleteResponse> createToken(
    @RequestBody LoginDto.LoginIdPasswordLoginRequest request) {

    LoginDto.LoginCompleteResponse loginCompleteResponse =
      userService.loginIdPasswordJwtLogin(request.getLoginId(), request.getPassword());

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<LoginDto.LoginCompleteResponse> refreshToken(
    @RequestBody LoginDto.TokenRefreshRequest request) {

    return ResponseEntity.ok(userService.refreshToken(request.getRefreshToken()));
  }
  
  @GetMapping("/oauth2-authorization-urls")
  public ResponseEntity<Map<String, Oauth2ProviderDto>> getAuthorizationUrls() {

    return ResponseEntity.ok(oauth2Service.generateAuthorizationUrls());
  }
}
