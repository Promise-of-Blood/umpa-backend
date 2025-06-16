package promiseofblood.umpabackend.dto;

import java.time.LocalDateTime;
import lombok.*;
import promiseofblood.umpabackend.domain.entitiy.User;

@Getter
@Builder
@ToString
public class UserDto {

  private Long id;

  private String username;

  private String gender;

  private String profileImageUrl;

  private String major;

  private String lessonStyle;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Oauth2UserDto oauth2User;

  static public UserDto of(User user) {
    return UserDto.builder()
      .id(user.getId())
      .username(user.getUsername())
      .gender(user.getGender().name())
      .profileImageUrl(user.getProfileImageUrl())
      .major(user.getMajor().name())
      .oauth2User(Oauth2UserDto.of(user.getOauth2User()))
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .build();
  }
}
