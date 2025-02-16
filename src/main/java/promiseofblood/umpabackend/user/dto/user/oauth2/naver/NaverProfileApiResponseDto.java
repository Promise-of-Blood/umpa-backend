package promiseofblood.umpabackend.user.dto.user.oauth2.naver;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;


@ToString
@Getter
public class NaverProfileApiResponseDto {

  private String resultcode;
  private String message;

  @NotNull
  private NaverProfileResponse response;

  @ToString
  @Getter
  public static class NaverProfileResponse {
    private String id;
    private String nickname;
    private String email;
    @JsonProperty("profile_image")
    private String profileImage;
  }
}
