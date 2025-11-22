package kr.co.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.beans.PropertyEditorSupport;
import kr.co.umpabackend.application.service.ProfileService;
import kr.co.umpabackend.application.service.UserService;
import kr.co.umpabackend.infrastructure.security.SecurityUserDetails;
import kr.co.umpabackend.web.schema.request.PatchDefaultProfileRequest;
import kr.co.umpabackend.web.schema.request.PatchStudentProfileRequest;
import kr.co.umpabackend.web.schema.request.PatchTeacherProfileRequest;
import kr.co.umpabackend.web.schema.response.RetrieveFullProfileResponse;
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

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "프로필 관리 API")
@RequiredArgsConstructor
public class ProfileController {

  private final UserService userService;
  private final ProfileService profileService;

  @GetMapping("/me")
  public ResponseEntity<RetrieveFullProfileResponse> getCurrentUser(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    return ResponseEntity.ok(userService.getUserByLoginId(securityUserDetails.getUsername()));
  }

  @PatchMapping("/me/teacher-profile")
  public ResponseEntity<RetrieveFullProfileResponse> patchTeacherProfile(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @RequestBody PatchTeacherProfileRequest teacherProfileRequest) {

    RetrieveFullProfileResponse teacherProfileResponse =
        profileService.patchTeacherProfile(
            securityUserDetails.getUsername(), teacherProfileRequest);

    return ResponseEntity.ok(teacherProfileResponse);
  }

  @PatchMapping("/me/student-profile")
  public ResponseEntity<RetrieveFullProfileResponse> patchStudentProfile(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @RequestBody @Valid PatchStudentProfileRequest patchStudentProfileRequest) {

    RetrieveFullProfileResponse studentProfileDto =
        profileService.patchStudentProfile(
            securityUserDetails.getUsername(), patchStudentProfileRequest);

    return ResponseEntity.ok(studentProfileDto);
  }

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
