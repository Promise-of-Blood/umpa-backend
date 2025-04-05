package promiseofblood.umpabackend.dto.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Oauth2UserInfoResponse {

  private String socialId;

  private String profileImageUrl;

  private String accessToken;

  private String refreshToken;

  private Long oauth2ProviderId;

}
