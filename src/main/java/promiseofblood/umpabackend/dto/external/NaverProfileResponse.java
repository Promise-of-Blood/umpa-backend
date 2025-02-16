package promiseofblood.umpabackend.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class NaverProfileResponse {

  private String resultcode;
  private String message;

  @NotNull
  private NaverProfileResponseBody response;

  @ToString
  @Getter
  public static class NaverProfileResponseBody {
    private String id;
    private String nickname;
    private String email;
    @JsonProperty("profile_image")
    private String profileImage;
  }
}
