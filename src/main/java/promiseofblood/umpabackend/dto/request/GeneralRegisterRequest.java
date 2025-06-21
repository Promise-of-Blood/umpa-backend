package promiseofblood.umpabackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GeneralRegisterRequest {

  @NotBlank
  private String loginId;

  @NotBlank
  private String password;

}
