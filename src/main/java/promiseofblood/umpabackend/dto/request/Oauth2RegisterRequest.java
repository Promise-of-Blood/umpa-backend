package promiseofblood.umpabackend.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class Oauth2RegisterRequest {
  @NotBlank
  private String oauth2Provider;

  @NotNull
  private String userType;

  @NotBlank
  @Size(min = 2, max = 20)
  @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "이름은 영문, 숫자, 한글만 가능합니다.")
  private String name;

  @NotBlank
  private String major;

  @Size(min = 3, max = 3)
  private List<String> wantedColleges;

  @NotBlank
  private String accessToken;

  @NotBlank
  private String refreshToken;
}
