package promiseofblood.umpabackend.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import promiseofblood.umpabackend.domain.entity.User;

@Getter
@Builder
@ToString
public class UserDto {

  private Long id;

  private String loginId;

  private String username;

  private String gender;

  private String profileImageUrl;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Oauth2UserDto oauth2User;

  static public UserDto of(User user) {

    return UserDto.builder()
      .id(user.getId())
      .loginId(user.getLoginId())
      .username(user.getUsername())
      .gender(user.getGender() == null ? null : user.getGender().name())
      .profileImageUrl(user.getProfileImageUrl())
      .oauth2User(user.getOauth2User() == null ? null : Oauth2UserDto.of(user.getOauth2User()))
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .build();
  }
}
