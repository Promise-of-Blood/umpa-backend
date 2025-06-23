package promiseofblood.umpabackend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JwtPairDto {

  String accessToken;

  String refreshToken;

}
