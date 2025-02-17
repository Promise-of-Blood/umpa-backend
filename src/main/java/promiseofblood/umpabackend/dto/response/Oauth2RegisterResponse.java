package promiseofblood.umpabackend.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.User;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class Oauth2RegisterResponse {
  private String name;
  private String profileImageUrl;
  private String major;
  private String oauth2Provider;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static Oauth2RegisterResponse of(User user) {
    return Oauth2RegisterResponse.builder()
            .name(user.getName())
            .profileImageUrl(user.getProfileImageUrl())
            .major(user.getMajor().getName())
            .oauth2Provider(user.getSocialUser().getOauth2Provider().getName())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
  }
}
