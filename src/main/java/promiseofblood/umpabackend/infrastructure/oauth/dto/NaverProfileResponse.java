package promiseofblood.umpabackend.infrastructure.oauth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NaverProfileResponse {

  @Getter
  @ToString
  @AllArgsConstructor
  public static class Response {

    private String id;

    private String nickname;

    @JsonProperty("profile_image")
    private String profileImage;
  }

  private String resultCode;

  private String message;

  private Response response;
}
