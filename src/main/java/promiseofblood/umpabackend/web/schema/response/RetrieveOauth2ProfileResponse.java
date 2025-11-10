package promiseofblood.umpabackend.web.schema.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.Oauth2User;

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
