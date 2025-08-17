package promiseofblood.umpabackend.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
