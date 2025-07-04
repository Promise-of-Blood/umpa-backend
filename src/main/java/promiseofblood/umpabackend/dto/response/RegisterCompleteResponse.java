package promiseofblood.umpabackend.dto.response;

import lombok.*;

import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.UserDto;

@Getter
@Builder
public class RegisterCompleteResponse {

  private UserDto user;

  private JwtPairDto jwtPair;

}
