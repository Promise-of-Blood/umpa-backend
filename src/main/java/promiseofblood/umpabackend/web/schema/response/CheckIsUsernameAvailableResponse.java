package promiseofblood.umpabackend.web.schema.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckIsUsernameAvailableResponse {

  private String username;

  private boolean isAvailable;

  private String message;
}
