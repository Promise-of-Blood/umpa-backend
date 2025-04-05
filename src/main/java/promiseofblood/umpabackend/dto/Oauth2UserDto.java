package promiseofblood.umpabackend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entitiy.Oauth2User;

@Builder
@Getter
@AllArgsConstructor
public class Oauth2UserDto {

  private String socialId;

  private String accessToken;

  private String refreshToken;

  private Long oauth2ProviderId;

  public static Oauth2UserDto of(Oauth2User oauth2User) {
    return Oauth2UserDto.builder()
      .socialId(oauth2User.getSocialId())
      .accessToken(oauth2User.getAccessToken())
      .refreshToken(oauth2User.getRefreshToken())
      .oauth2ProviderId(oauth2User.getOauth2Provider().getId())
      .build();
  }
}
