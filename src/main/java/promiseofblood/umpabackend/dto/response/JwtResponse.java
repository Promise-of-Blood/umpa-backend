package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtResponse {
  String accessToken;
  String refreshToken;
}
