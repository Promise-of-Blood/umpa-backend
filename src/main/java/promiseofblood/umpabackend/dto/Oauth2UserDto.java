package promiseofblood.umpabackend.dto;

import lombok.Builder;
import lombok.Getter;

import promiseofblood.umpabackend.domain.entity.Oauth2User;

@Getter
@Builder
public class Oauth2UserDto {

  private String providerName;

  private String providerUid;

  private String profileImageUrl;

  private String username;

  public static Oauth2UserDto of(Oauth2User oauth2User) {
    return Oauth2UserDto.builder()
      .providerName(oauth2User.getProviderName())
      .providerUid(oauth2User.getProviderUid())
      .profileImageUrl(oauth2User.getProfileImageUrl())
      .username(oauth2User.getUsername())
      .build();
  }

}
