package promiseofblood.umpabackend.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.Status;

@Getter
@Builder
@ToString
public class UserDto {

  private Long id;

  private String loginId;

  private String username;

  private Status status;

  private Role role;

  private String gender;

  private String profileImageUrl;

  private String profileType;

  private TeacherProfileDto teacherProfile;

  private StudentProfileDto studentProfile;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Oauth2UserDto oauth2User;

  static public UserDto of(User user) {

    return UserDto.builder()
      .id(user.getId())
      .loginId(user.getLoginId())
      .username(user.getUsername())
      .status(user.getStatus())
      .role(user.getRole())
      .gender(user.getGender() == null ? null : user.getGender().name())
      .profileImageUrl(user.getProfileImageUrl())
      .profileType(user.getProfileType().name())
      .teacherProfile(
        user.getTeacherProfile() == null ? null : TeacherProfileDto.of(user.getTeacherProfile())
      )
      .studentProfile(
        user.getStudentProfile() == null ? null : StudentProfileDto.of(user.getStudentProfile())
      )
      .oauth2User(user.getOauth2User() == null ? null : Oauth2UserDto.of(user.getOauth2User()))
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .build();
  }
}
