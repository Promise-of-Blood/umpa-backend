package promiseofblood.umpabackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.time.LocalDateTime;

import promiseofblood.umpabackend.domain.College;
import promiseofblood.umpabackend.domain.User;

@Builder
@Getter
@AllArgsConstructor
public class Oauth2RegisterResponse {
  private String userType;
  private String name;
  private String profileImageUrl;
  private String major;
  private List<String> wantedColleges;
  private String oauth2Provider;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  JwtResponse jwt;

  public static Oauth2RegisterResponse of(User user, String accessToken) {
    return Oauth2RegisterResponse.builder()
            .userType(user.getUserType().getName())
            .name(user.getName())
            .profileImageUrl(user.getProfileImageUrl())
            .major(user.getMajor().getName())
            .wantedColleges(
                    user.getWantedColleges().stream()
                            .map(College::getName)
                            .toList()
            )
            .oauth2Provider(user.getSocialUser().getOauth2Provider().getName())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .jwt(JwtResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(null)
                    .build()
            )
            .build();
  }
}
