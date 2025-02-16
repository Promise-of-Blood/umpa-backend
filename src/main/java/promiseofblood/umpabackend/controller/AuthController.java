package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.external.NaverTokenResponse;
import promiseofblood.umpabackend.dto.response.Oauth2ProviderLoginUrlResponse;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.service.Oauth2Service;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "인증 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final Oauth2Service oauth2Service;

  @Operation(
          summary = "소셜 회원가입",
          description = "소셜 로그인을 통해 회원가입합니다."
  )
  @PostMapping("/register/oauth2")
  public ResponseEntity<UserDto> registerWithNaver(
          @RequestBody Oauth2RegisterRequest oauth2RegisterRequest
  ) {
    UserDto socialUserDto = oauth2Service.register(oauth2RegisterRequest);

    return ResponseEntity.ok(socialUserDto);
  }

  @Operation(
          summary = "네이버 액세스 토큰 발급 (테스트용 API)",
          description = "콜백 코드를 이용해, 네이버 api 를 호출할 수 있는 액세스 토큰을 발급받습니다.<br />" +
                  "내부 개발용으로만 사용하세요."
  )
  @GetMapping("/register/oauth2/naver/access-token")
  public NaverTokenResponse getAccessToken(@RequestParam String code) {

    return oauth2Service.getAccessToken(code);
  }

  @Operation(
          summary = "네이버 로그인 url 조회 (테스트용 API)",
          description = "네이버로 회원가입하기 위한 url을 반환합니다.<br />" +
                  "개발용으로만 사용하세요."
  )
  @GetMapping("/register/oauth2/naver/url")
  public Oauth2ProviderLoginUrlResponse getNaverUrl() {

    return new Oauth2ProviderLoginUrlResponse(oauth2Service.getLoginUrl());
  }

}
