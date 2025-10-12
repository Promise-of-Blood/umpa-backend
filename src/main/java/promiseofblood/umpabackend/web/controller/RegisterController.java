package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.Oauth2Service;
import promiseofblood.umpabackend.application.service.UserService;
import promiseofblood.umpabackend.web.schema.request.RegisterByLoginIdPasswordRequest;
import promiseofblood.umpabackend.web.schema.request.RegisterByOauth2Request;
import promiseofblood.umpabackend.web.schema.response.CheckIsOauth2RegisterAvailableResponse;
import promiseofblood.umpabackend.web.schema.response.CheckIsUsernameAvailableResponse;
import promiseofblood.umpabackend.web.schema.response.LoginCompleteResponse;

@RestController
@RequestMapping("/api/v1/users/register")
@RequiredArgsConstructor
@Tag(name = "회원가입 API")
public class RegisterController {

  private final UserService userService;
  private final Oauth2Service oauth2Service;

  @Tag(name = "회원가입 API")
  @PostMapping(value = "/general", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<LoginCompleteResponse> registerUser(
      @Validated @ModelAttribute RegisterByLoginIdPasswordRequest loginIdPasswordRequest) {

    LoginCompleteResponse loginCompleteResponse = userService.registerUser(loginIdPasswordRequest);

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @Tag(name = "회원가입 API")
  @PostMapping(value = "/{providerName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<LoginCompleteResponse> registerOauth2User(
      @PathVariable String providerName,
      @Validated @ModelAttribute RegisterByOauth2Request oauth2RegisterRequest) {

    LoginCompleteResponse loginCompleteResponse =
        oauth2Service.registerOauth2User(providerName, oauth2RegisterRequest);

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @Tag(name = "회원가입 API")
  @GetMapping(value = "/check/username")
  public ResponseEntity<CheckIsUsernameAvailableResponse> isUsernameAvailable(
      @RequestParam String username) {

    return ResponseEntity.ok(userService.isUsernameAvailable(username));
  }

  @Tag(name = "회원가입 API", description = "이미 가입한 OAuth2 사용자인지 확인합니다.")
  @GetMapping(value = "/check/oauth2")
  public ResponseEntity<CheckIsOauth2RegisterAvailableResponse> isOauth2Available(
      @RequestParam @NotNull String providerName,
      @RequestParam String accessToken,
      @RequestParam String idToken) {

    return ResponseEntity.ok(
        oauth2Service.isOauth2RegisterAvailable(providerName, idToken, accessToken));
  }
}
