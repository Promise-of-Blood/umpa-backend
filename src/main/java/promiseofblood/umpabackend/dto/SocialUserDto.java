package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.SocialUser;

@Builder
@Getter
@AllArgsConstructor
public class SocialUserDto {

  private String socialId;

  private String accessToken;

  private String refreshToken;

  private Long userId;

  private Long oauth2ProviderId;

  public static SocialUserDto of(SocialUser socialUser) {
    return SocialUserDto.builder()
            .socialId(socialUser.getSocialId())
            .accessToken(socialUser.getAccessToken())
            .refreshToken(socialUser.getRefreshToken())
            .userId(socialUser.getUser().getId())
            .oauth2ProviderId(socialUser.getOauth2Provider().getId())
            .build();
  }
}
