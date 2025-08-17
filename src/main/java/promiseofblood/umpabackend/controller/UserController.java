package promiseofblood.umpabackend.controller;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.core.security.SecurityUserDetails;
import promiseofblood.umpabackend.domain.service.Oauth2Service;
import promiseofblood.umpabackend.domain.service.ProfileService;
import promiseofblood.umpabackend.domain.service.UserService;
import promiseofblood.umpabackend.dto.LoginDto;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;
import promiseofblood.umpabackend.dto.StudentProfileDto;
import promiseofblood.umpabackend.dto.TeacherProfileDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ProfileService profileService;
  private final Oauth2Service oauth2Service;

  // ****************
  // * 사용자 관리 API *
  // ****************
  @Tag(name = "사용자 관리 API")
  @GetMapping("")
  public ResponseEntity<List<UserDto.ProfileResponse>> getUser() {
    List<UserDto.ProfileResponse> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  // **************
  // * 회원가입 API *
  // **************
  @Tag(name = "회원가입 API")
  @PostMapping(value = "/register/general", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<LoginDto.LoginCompleteResponse> registerUser(
    @Validated @ModelAttribute LoginDto.LoginIdPasswordRegisterRequest loginIdPasswordRequest) {

    LoginDto.LoginCompleteResponse loginCompleteResponse = userService.registerUser(
      loginIdPasswordRequest);

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @Tag(name = "회원가입 API")
  @PostMapping(value = "/register/{providerName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<LoginDto.LoginCompleteResponse> registerOauth2User(
    @PathVariable String providerName,
    @Validated @ModelAttribute LoginDto.Oauth2RegisterRequest oauth2RegisterRequest) {

    LoginDto.LoginCompleteResponse loginCompleteResponse = oauth2Service.registerOauth2User(
      providerName, oauth2RegisterRequest);

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @Tag(name = "회원가입 API")
  @GetMapping(value = "/register/check/username")
  public ResponseEntity<LoginDto.IsUsernameAvailableResponse> isUsernameAvailable(
    @RequestParam String username) {

    return ResponseEntity.ok(userService.isUsernameAvailable(username));
  }

  @Tag(name = "회원가입 API", description = "이미 가입한 OAuth2 사용자인지 확인합니다.")
  @GetMapping(value = "/register/check/oauth2")
  public ResponseEntity<LoginDto.IsUsernameAvailableResponse> isOauth2Available(
    @RequestParam String username) {

    return ResponseEntity.ok(userService.isUsernameAvailable(username));
  }

  // ****************
  // * 프로필 관리 API *
  // ****************
  @Tag(name = "프로필 관리 API")
  @GetMapping("/me")
  public ResponseEntity<UserDto.ProfileResponse> getCurrentUser(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    return ResponseEntity.ok(userService.getUserByLoginId(securityUserDetails.getUsername()));
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping("/me/teacher-profile")
  public ResponseEntity<UserDto.ProfileResponse> patchTeacherProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody TeacherProfileDto.TeacherProfileRequest teacherProfileRequest) {

    UserDto.ProfileResponse teacherProfileResponse = profileService.patchTeacherProfile(
      securityUserDetails.getUsername(), teacherProfileRequest);

    return ResponseEntity.ok(teacherProfileResponse);
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping("/me/student-profile")
  public ResponseEntity<UserDto.ProfileResponse> patchStudentProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody @Valid StudentProfileDto.StudentProfileRequest studentProfileRequest) {

    UserDto.ProfileResponse studentProfileDto = profileService.patchStudentProfile(
      securityUserDetails.getUsername(), studentProfileRequest);

    return ResponseEntity.ok(studentProfileDto);
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping(value = "/me/default-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UserDto.ProfileResponse> patchDefaultProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @Validated @ModelAttribute UserDto.DefaultProfilePatchRequest defaultProfilePatchRequest) {

    UserDto.ProfileResponse updatedUser = profileService.patchDefaultProfile(
      securityUserDetails.getUsername(), defaultProfilePatchRequest);

    return ResponseEntity.ok(updatedUser);
  }

  // ***************
  // * 토큰 발급 API *
  // ***************
  @Tag(name = "토큰 발급 API")
  @PostMapping("token/{providerName}")
  public ResponseEntity<LoginDto.LoginCompleteResponse> createOauth2Token(
    @PathVariable String providerName,
    @RequestBody LoginDto.Oauth2LoginRequest oauth2LoginRequest) {

    LoginDto.LoginCompleteResponse loginCompleteResponse = oauth2Service.generateOauth2Jwt(
      providerName, oauth2LoginRequest.getExternalIdToken(),
      oauth2LoginRequest.getExternalAccessToken());

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @Tag(name = "토큰 발급 API")
  @PostMapping("/token/general")
  public ResponseEntity<LoginDto.LoginCompleteResponse> createToken(
    @RequestBody LoginDto.LoginIdPasswordLoginRequest request) {

    LoginDto.LoginCompleteResponse loginCompleteResponse = userService.loginIdPasswordJwtLogin(
      request.getLoginId(), request.getPassword());

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @Tag(name = "토큰 발급 API")
  @PostMapping("/refresh-token")
  public ResponseEntity<LoginDto.LoginCompleteResponse> refreshToken(
    @RequestBody LoginDto.TokenRefreshRequest request) {

    return ResponseEntity.ok(userService.refreshToken(request.getRefreshToken()));
  }

  @Tag(name = "토큰 발급 API")
  @GetMapping("/oauth2-authorization-urls")
  public ResponseEntity<Map<String, Oauth2ProviderDto>> getAuthorizationUrls() {

    return ResponseEntity.ok(oauth2Service.generateAuthorizationUrls());
  }


  @GetMapping("/callback/{providerName}")
  @Hidden
  public Oauth2ProfileResponse oauth2AuthorizationCallback(@PathVariable String providerName,
    String code) {

    return oauth2Service.getOauth2Profile(providerName, code);
  }


  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(MultipartFile.class, new PropertyEditorSupport() {
      @Override
      public void setAsText(String text) {
        setValue(null);
      }
    });
  }
}
