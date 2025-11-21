package kr.co.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import kr.co.umpabackend.domain.entity.Oauth2User;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.vo.Role;
import kr.co.umpabackend.domain.vo.UserStatus;
import lombok.Builder;
import lombok.Getter;

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
  private RetrieveTeacherProfileResponse teacherProfile;

  @Schema(nullable = true)
  private RetrieveStudentProfileResponse studentProfile;

  @Schema(nullable = true)
  private RetrieveOauth2ProfileResponse oauth2User;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  public static RetrieveFullProfileResponse from(User user, Oauth2User oauth2User) {
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
                : RetrieveTeacherProfileResponse.from(user.getTeacherProfile()))
        .studentProfile(
            user.getStudentProfile() == null
                ? null
                : RetrieveStudentProfileResponse.from(user.getStudentProfile()))
        .oauth2User(oauth2User == null ? null : RetrieveOauth2ProfileResponse.of(oauth2User))
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
