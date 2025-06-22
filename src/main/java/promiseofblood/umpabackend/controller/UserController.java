package promiseofblood.umpabackend.controller;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promiseofblood.umpabackend.domain.service.Oauth2Service;
import promiseofblood.umpabackend.domain.service.UserService;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.GeneralLoginRequest;
import promiseofblood.umpabackend.dto.request.GeneralRegisterRequest;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.request.TokenRefreshRequest;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "사용자 관리 API", description = "사용자 등록, 조회, 삭제 및 OAuth2 인증 관련 API")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final Oauth2Service oauth2Service;

  @PostMapping("/register/general")
  public ResponseEntity<RegisterCompleteResponse> registerUser(
    @RequestBody @Valid final GeneralRegisterRequest generalRegisterRequest) {
    RegisterCompleteResponse registerCompleteResponse = userService.registerUser(
      generalRegisterRequest);

    return ResponseEntity.ok(registerCompleteResponse);
  }

  @PostMapping(value = "/register/{providerName}")
  public ResponseEntity<RegisterCompleteResponse> registerOauth2User(
    @PathVariable String providerName,
    @RequestBody Oauth2RegisterRequest oauth2RegisterRequest
  ) {

    RegisterCompleteResponse registerCompleteResponse = oauth2Service.registerOauth2User(
      providerName, oauth2RegisterRequest);

    return ResponseEntity.ok(registerCompleteResponse);
  }

  @GetMapping("")
  public ResponseEntity<List<UserDto>> getUser() {
    List<UserDto> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
    UserDto user = userService.getUsers().stream()
      .filter(u -> u.getId().equals(userId))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("User not found"));

    return ResponseEntity.ok(user);
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> getCurrentUser() {
    UserDto user = userService.getUsers().stream()
      .findFirst()
      .orElseThrow(() -> new RuntimeException("No current user found"));

    return ResponseEntity.ok(user);
  }

  @DeleteMapping("")
  public ResponseEntity<Void> deleteUser() {
    userService.deleteUsers();

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/callback/{providerName}")
  @Hidden
  public Oauth2ProfileResponse oauth2AuthorizationCallback(
    @PathVariable String providerName,
    String code
  ) {

    return oauth2Service.getOauth2Profile(providerName, code);
  }

  @PostMapping("/token")
  public ResponseEntity<JwtPairDto> createToken(@RequestBody GeneralLoginRequest request) {

    JwtPairDto jwtPairDto = userService.generateJwt(request.getLoginId(), request.getPassword());

    return ResponseEntity.ok(jwtPairDto);
  }

  @PostMapping("/token/refresh")
  public JwtPairDto refreshToken(@RequestBody TokenRefreshRequest request) {

    return oauth2Service.refreshToken(request.getRefreshToken());
  }

  @GetMapping("/oauth2-authorization-urls")
  public ResponseEntity<Map<String, Oauth2ProviderDto>> getAuthorizationUrls() {

    return ResponseEntity.ok(oauth2Service.generateAuthorizationUrls());
  }

}
