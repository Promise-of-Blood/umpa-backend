package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.application.service.ProfileService;
import promiseofblood.umpabackend.application.service.UserService;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.request.PatchDefaultProfileRequest;
import promiseofblood.umpabackend.web.schema.request.PatchStudentProfileRequest;
import promiseofblood.umpabackend.web.schema.request.PatchTeacherProfileRequest;
import promiseofblood.umpabackend.web.schema.response.RetrieveFullProfileResponse;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ProfileService profileService;

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
      @RequestBody PatchTeacherProfileRequest teacherProfileRequest) {

    RetrieveFullProfileResponse teacherProfileResponse =
        profileService.patchTeacherProfile(
            securityUserDetails.getUsername(), teacherProfileRequest);

    return ResponseEntity.ok(teacherProfileResponse);
  }

  @Tag(name = "프로필 관리 API")
  @PatchMapping("/me/student-profile")
  public ResponseEntity<RetrieveFullProfileResponse> patchStudentProfile(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @RequestBody @Valid PatchStudentProfileRequest patchStudentProfileRequest) {

    RetrieveFullProfileResponse studentProfileDto =
        profileService.patchStudentProfile(
            securityUserDetails.getUsername(), patchStudentProfileRequest);

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
