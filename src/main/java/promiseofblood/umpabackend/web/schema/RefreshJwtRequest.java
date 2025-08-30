package promiseofblood.umpabackend.web.schema;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshJwtRequest {

  @NotBlank
  private final String refreshToken;
}
