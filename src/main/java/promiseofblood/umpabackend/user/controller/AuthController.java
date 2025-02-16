package promiseofblood.umpabackend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promiseofblood.umpabackend.user.dto.RegisterRequestDto;
import promiseofblood.umpabackend.user.dto.oauth2.naver.NaverTokenApiResponseDto;
import promiseofblood.umpabackend.user.dto.oauth2.naver.NaverLoginUrlResponseDto;
import promiseofblood.umpabackend.user.dto.user.UserDto;
import promiseofblood.umpabackend.user.service.OAuth2Service;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "인증 API")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final OAuth2Service oAuth2Service;

  @Operation(
          summary = "구글로 회원가입",
          description = "구글에서 발급한 토큰을 받아 회원가입합니다."
  )
  @PostMapping("/register/oauth2/google")
  public void registerWithGoogle() {
  }

  @Operation(
          summary = "카카오로 회원가입",
          description = "카카오에서 발급한 토큰을 받아 회원가입합니다."
  )
  @PostMapping("/register/oauth2/kakao")
  public void registerWithKakao() {
  }

  @Operation(
          summary = "네이버로 회원가입",
          description = "네이버에서 발급한 토큰을 받아 회원가입합니다."
  )
  @PostMapping("/register/oauth2/naver")
  public ResponseEntity<UserDto> registerWithNaver(@RequestBody RegisterRequestDto registerRequestDto) {

    UserDto socialUserDto = oAuth2Service.registerWithNaver(registerRequestDto);

    return ResponseEntity.ok(socialUserDto);
  }

  @Operation(
          summary = "네이버 액세스 토큰 발급 (테스트용 API)",
          description = "콜백 코드를 이용해, 네이버 api 를 호출할 수 있는 액세스 토큰을 발급받습니다.<br />" +
                  "내부 개발용으로만 사용하세요."
  )
  @GetMapping("/register/oauth2/naver/access-token")
  public NaverTokenApiResponseDto getAccessToken(@RequestParam String code) {

    return oAuth2Service.getAccessToken(code);
  }

  @Operation(
          summary = "네이버 로그인 url 조회 (테스트용 API)",
          description = "네이버로 회원가입하기 위한 url을 반환합니다.<br />" +
                  "개발용으로만 사용하세요."
  )
  @GetMapping("/register/oauth2/naver/url")
  public NaverLoginUrlResponseDto getNaverUrl() {

    return new NaverLoginUrlResponseDto(oAuth2Service.getLoginUrl());
  }

}
