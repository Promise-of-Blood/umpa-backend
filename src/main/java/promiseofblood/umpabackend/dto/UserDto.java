package promiseofblood.umpabackend.dto;

import java.time.LocalDateTime;
import lombok.*;
import promiseofblood.umpabackend.domain.entity.User;

@Getter
@Builder
@ToString
public class UserDto {

  private Long id;

  private String username;

  private String gender;

  private String profileImageUrl;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private Oauth2UserDto oauth2User;

  static public UserDto ofInitialUser(User user) {
    return UserDto.builder()
      .id(user.getId())
      .oauth2User(Oauth2UserDto.of(user.getOauth2User()))
      .createdAt(user.getCreatedAt())
      .updatedAt(user.getUpdatedAt())
      .build();
  }
}
