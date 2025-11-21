package kr.co.umpabackend.web.schema.response;

import kr.co.umpabackend.domain.entity.Oauth2User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetrieveOauth2ProfileResponse {

  private String providerName;

  private String providerUid;

  private String profileImageUrl;

  private String username;

  public static RetrieveOauth2ProfileResponse of(Oauth2User oauth2User) {
    return RetrieveOauth2ProfileResponse.builder()
        .providerName(oauth2User.getProviderName())
        .providerUid(oauth2User.getProviderUid())
        .profileImageUrl(oauth2User.getProfileImageUrl())
        .username(oauth2User.getUsername())
        .build();
  }
}
