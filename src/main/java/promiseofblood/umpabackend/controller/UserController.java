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
import promiseofblood.umpabackend.domain.service.UserService;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.LoginDto;
import promiseofblood.umpabackend.dto.LoginDto.AuthenticationCompleteResponse;
import promiseofblood.umpabackend.dto.LoginDto.LoginIdPasswordLoginRequest;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2LoginRequest;
import promiseofblood.umpabackend.dto.request.StudentProfileRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;
import promiseofblood.umpabackend.dto.request.TokenRefreshRequest;
import promiseofblood.umpabackend.dto.response.IsUsernameAvailableResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
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
  @PostMapping("/register/general")
  public ResponseEntity<AuthenticationCompleteResponse> registerUser(
    @RequestBody @Valid final LoginDto.LoginIdPasswordRegisterRequest loginIdPasswordRequest) {

    AuthenticationCompleteResponse authenticationCompleteResponse = userService.registerUser(
      loginIdPasswordRequest);

    return ResponseEntity.ok(authenticationCompleteResponse);
  }

  @Tag(name = "회원가입 API")
  @PostMapping(value = "/register/{providerName}")
  public ResponseEntity<AuthenticationCompleteResponse> registerOauth2User(
    @PathVariable String providerName,
    @RequestBody LoginDto.Oauth2RegisterRequest oauth2RegisterRequest
  ) {

    AuthenticationCompleteResponse authenticationCompleteResponse = oauth2Service.registerOauth2User(
      providerName, oauth2RegisterRequest);

    return ResponseEntity.ok(authenticationCompleteResponse);
  }

  @Tag(name = "회원가입 API")
  @GetMapping(value = "/register/check/username")
  public ResponseEntity<IsUsernameAvailableResponse> isUsernameAvailable(
    @RequestParam String username
  ) {

    return ResponseEntity.ok(
      userService.isUsernameAvailable(username)
    );
  }

  // ****************
  // * 프로필 관리 API *
  // ****************
  @Tag(name = "프로필 관리 API")
  @GetMapping("/me")
  public ResponseEntity<UserDto.ProfileResponse> getCurrentUser(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    return ResponseEntity.ok(
      userService.getUserByLoginId(securityUserDetails.getUsername())
    );
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping("/me/teacher-profile")
  public ResponseEntity<UserDto.ProfileResponse> patchTeacherProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody TeacherProfileRequest teacherProfileRequest
  ) {

    UserDto.ProfileResponse teacherProfileResponse = userService.patchTeacherProfile(
      securityUserDetails.getUsername(), teacherProfileRequest
    );

    return ResponseEntity.ok(teacherProfileResponse);
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping("/me/student-profile")
  public ResponseEntity<UserDto.ProfileResponse> patchStudentProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody @Valid StudentProfileRequest studentProfileRequest
  ) {

    UserDto.ProfileResponse studentProfileDto = userService.patchStudentProfile(
      securityUserDetails.getUsername(), studentProfileRequest
    );

    return ResponseEntity.ok(studentProfileDto);
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping(value = "/me/default-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UserDto.ProfileResponse> patchDefaultProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @Validated @ModelAttribute UserDto.DefaultProfilePatchRequest defaultProfilePatchRequest
  ) {

    UserDto.ProfileResponse updatedUser = userService.patchDefaultProfile(
      securityUserDetails.getUsername(), defaultProfilePatchRequest
    );

    return ResponseEntity.ok(updatedUser);
  }

  // ***************
  // * 토큰 발급 API *
  // ***************
  @Tag(name = "토큰 발급 API")
  @PostMapping("token/{providerName}")
  public ResponseEntity<LoginDto.AuthenticationCompleteResponse> createOauth2Token(
    @PathVariable String providerName,
    @RequestBody Oauth2LoginRequest oauth2LoginRequest
  ) {

    LoginDto.AuthenticationCompleteResponse authenticationCompleteResponse = oauth2Service.generateOauth2Jwt(
      providerName,
      oauth2LoginRequest.getExternalIdToken(),
      oauth2LoginRequest.getExternalAccessToken()
    );

    return ResponseEntity.ok(authenticationCompleteResponse);
  }

  @Tag(name = "토큰 발급 API")
  @PostMapping("/token/general")
  public ResponseEntity<LoginDto.AuthenticationCompleteResponse> createToken(
    @RequestBody LoginIdPasswordLoginRequest request) {

    LoginDto.AuthenticationCompleteResponse authenticationCompleteResponse = userService.loginIdPasswordJwtLogin(
      request.getLoginId(),
      request.getPassword()
    );

    return ResponseEntity.ok(authenticationCompleteResponse);
  }

  @Tag(name = "토큰 발급 API")
  @PostMapping("/refresh-token")
  public JwtPairDto refreshToken(@RequestBody TokenRefreshRequest request) {

    return userService.refreshToken(request.getRefreshToken());
  }

  @Tag(name = "토큰 발급 API")
  @GetMapping("/oauth2-authorization-urls")
  public ResponseEntity<Map<String, Oauth2ProviderDto>> getAuthorizationUrls() {

    return ResponseEntity.ok(oauth2Service.generateAuthorizationUrls());
  }


  @GetMapping("/callback/{providerName}")
  @Hidden
  public Oauth2ProfileResponse oauth2AuthorizationCallback(
    @PathVariable String providerName,
    String code
  ) {

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
