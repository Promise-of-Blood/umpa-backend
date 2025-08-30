package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.UserStatus;
import promiseofblood.umpabackend.dto.Oauth2UserDto;
import promiseofblood.umpabackend.dto.StudentProfileDto;
import promiseofblood.umpabackend.dto.TeacherProfileDto;

@Getter
@Builder
public class RetrieveFullProfileResponse {

  private Long id;

  private String loginId;

  @Schema(nullable = true)
  private String username;

  private UserStatus status;

  private Role role;

  @Schema(nullable = true)
  private String gender;

  @Schema(nullable = true)
  private String profileImageUrl;

  private String profileType;

  @Schema(nullable = true)
  private TeacherProfileDto.TeacherProfileResponse teacherProfile;

  @Schema(nullable = true)
  private StudentProfileDto.StudentProfileResponse studentProfile;

  @Schema(nullable = true)
  private Oauth2UserDto oauth2User;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static RetrieveFullProfileResponse from(User user) {
    return RetrieveFullProfileResponse.builder()
      .id(user.getId())
      .loginId(user.getLoginId())
      .username(user.getUsername().getValue())
      .status(user.getUserStatus())
      .role(user.getRole())
      .gender(user.getGender() == null ? null : user.getGender().name())
      .profileImageUrl(user.getProfileImageUrl())
      .profileType(user.getProfileType().name())
      .teacherProfile(
        user.getTeacherProfile() == null
          ? null
          : TeacherProfileDto.TeacherProfileResponse.from(user.getTeacherProfile()))
      .studentProfile(
        user.getStudentProfile() == null
          ? null
          : StudentProfileDto.StudentProfileResponse.from(user.getStudentProfile()))
      .oauth2User(user.getOauth2User() == null ? null : Oauth2UserDto.of(user.getOauth2User()))
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .build();
  }
}
