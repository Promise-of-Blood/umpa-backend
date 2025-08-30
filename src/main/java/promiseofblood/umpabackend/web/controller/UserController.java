package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.beans.PropertyEditorSupport;
import java.util.List;
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
import promiseofblood.umpabackend.application.service.Oauth2Service;
import promiseofblood.umpabackend.application.service.ProfileService;
import promiseofblood.umpabackend.application.service.UserService;
import promiseofblood.umpabackend.dto.LoginDto;
import promiseofblood.umpabackend.dto.StudentProfileDto;
import promiseofblood.umpabackend.dto.TeacherProfileDto;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.PatchDefaultProfileRequest;
import promiseofblood.umpabackend.web.schema.RetrieveFullProfileResponse;

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
  public ResponseEntity<List<RetrieveFullProfileResponse>> getUser() {
    List<RetrieveFullProfileResponse> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  // **************
  // * 회원가입 API *
  // **************
  @Tag(name = "회원가입 API")
  @PostMapping(value = "/register/general", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<LoginDto.LoginCompleteResponse> registerUser(
    @Validated @ModelAttribute LoginDto.LoginIdPasswordRegisterRequest loginIdPasswordRequest) {

    LoginDto.LoginCompleteResponse loginCompleteResponse =
      userService.registerUser(loginIdPasswordRequest);

    return ResponseEntity.ok(loginCompleteResponse);
  }

  @Tag(name = "회원가입 API")
  @PostMapping(value = "/register/{providerName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<LoginDto.LoginCompleteResponse> registerOauth2User(
    @PathVariable String providerName,
    @Validated @ModelAttribute LoginDto.Oauth2RegisterRequest oauth2RegisterRequest) {

    LoginDto.LoginCompleteResponse loginCompleteResponse =
      oauth2Service.registerOauth2User(providerName, oauth2RegisterRequest);

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
  public ResponseEntity<LoginDto.IsOauth2RegisterAvailableResponse> isOauth2Available(
    @RequestParam @NotNull String providerName,
    @RequestParam String accessToken,
    @RequestParam String idToken) {

    return ResponseEntity.ok(
      oauth2Service.isOauth2RegisterAvailable(providerName, idToken, accessToken));
  }

  // ****************
  // * 프로필 관리 API *
  // ****************
  @Tag(name = "프로필 관리 API")
  @GetMapping("/me")
  public ResponseEntity<RetrieveFullProfileResponse> getCurrentUser(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    return ResponseEntity.ok(userService.getUserByLoginId(securityUserDetails.getUsername()));
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping("/me/teacher-profile")
  public ResponseEntity<RetrieveFullProfileResponse> patchTeacherProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody TeacherProfileDto.TeacherProfileRequest teacherProfileRequest) {

    RetrieveFullProfileResponse teacherProfileResponse =
      profileService.patchTeacherProfile(
        securityUserDetails.getUsername(), teacherProfileRequest);

    return ResponseEntity.ok(teacherProfileResponse);
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping("/me/student-profile")
  public ResponseEntity<RetrieveFullProfileResponse> patchStudentProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody @Valid StudentProfileDto.StudentProfileRequest studentProfileRequest) {

    RetrieveFullProfileResponse studentProfileDto =
      profileService.patchStudentProfile(
        securityUserDetails.getUsername(), studentProfileRequest);

    return ResponseEntity.ok(studentProfileDto);
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping(value = "/me/default-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RetrieveFullProfileResponse> patchDefaultProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @Validated @ModelAttribute PatchDefaultProfileRequest patchDefaultProfileRequest) {

    RetrieveFullProfileResponse updatedUser =
      profileService.patchDefaultProfile(
        securityUserDetails.getUsername(), patchDefaultProfileRequest);

    return ResponseEntity.ok(updatedUser);
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(
      MultipartFile.class,
      new PropertyEditorSupport() {
        @Override
        public void setAsText(String text) {
          setValue(null);
        }
      });
  }
}
