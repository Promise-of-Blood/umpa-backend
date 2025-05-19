package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterCompleteResponse {

  private String accessToken;

  private String refreshToken;
  
}
