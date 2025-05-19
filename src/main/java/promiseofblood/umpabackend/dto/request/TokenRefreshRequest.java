package promiseofblood.umpabackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRefreshRequest {

  @NotBlank
  private String refreshToken;

}