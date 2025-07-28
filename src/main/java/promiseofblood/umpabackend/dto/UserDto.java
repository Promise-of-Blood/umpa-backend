package promiseofblood.umpabackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import javax.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.Status;

@Getter
@Builder
@ToString
public class UserDto {

  @Getter
  @Builder
  public static class ProfileResponse {

    private Long id;

    private String loginId;

    @Schema(nullable = true)
    private String username;

    private Status status;

    private Role role;

    @Schema(nullable = true)
    private String gender;

    @Schema(nullable = true)
    private String profileImageUrl;

    private String profileType;

    @Schema(nullable = true)
    private TeacherProfileDto teacherProfile;

    @Schema(nullable = true)
    private StudentProfileDto studentProfile;

    @Schema(nullable = true)
    private Oauth2UserDto oauth2User;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ProfileResponse from(User user) {
      return ProfileResponse.builder()
        .id(user.getId())
        .loginId(user.getLoginId())
        .username(user.getUsername())
        .status(user.getStatus())
        .role(user.getRole())
        .gender(user.getGender() == null ? null : user.getGender().name())
        .profileImageUrl(user.getProfileImageUrl())
        .profileType(user.getProfileType().name())
        .teacherProfile(
          user.getTeacherProfile() == null ? null : TeacherProfileDto.of(user.getTeacherProfile()
          ))
        .studentProfile(
          user.getStudentProfile() == null ? null : StudentProfileDto.of(user.getStudentProfile()
          ))
        .oauth2User(user.getOauth2User() == null ? null : Oauth2UserDto.of(user.getOauth2User()))
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
    }
  }

  @Getter
  @AllArgsConstructor
  public static class DefaultProfilePatchRequest {

    @Nullable
    @Schema(description = "사용자 닉네임", example = "홍길동")
    private String username;

    @Nullable
    @Schema(description = "성별", example = "MALE")
    private Gender gender;

    @Nullable
    @Schema(type = "string", format = "binary", description = "프로필 이미지 파일")
    private MultipartFile profileImage;

  }
}
