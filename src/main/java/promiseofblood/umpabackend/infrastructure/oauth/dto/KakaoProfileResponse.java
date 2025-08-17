package promiseofblood.umpabackend.infrastructure.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class KakaoProfileResponse {

  @Getter
  @ToString
  @AllArgsConstructor
  public static class Properties {

    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;

    @JsonProperty("thumbnail_image")
    private String thumbnailImage;
  }

  private String id;

  @JsonProperty("connected_at")
  private String connectedAt;

  @JsonProperty("properties")
  private Properties properties;
}
