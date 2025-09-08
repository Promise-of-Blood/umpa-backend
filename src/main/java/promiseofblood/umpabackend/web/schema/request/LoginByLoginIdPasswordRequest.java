package promiseofblood.umpabackend.web.schema.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginByLoginIdPasswordRequest {

  private final String loginId;

  private final String password;
}
