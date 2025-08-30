package promiseofblood.umpabackend.web.schema.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// ****************
// * 로그인 관련 DTO *
// ****************
@Getter
@RequiredArgsConstructor
public class LoginByOauth2Request {

  private final String externalIdToken;

  private final String externalAccessToken;
}
