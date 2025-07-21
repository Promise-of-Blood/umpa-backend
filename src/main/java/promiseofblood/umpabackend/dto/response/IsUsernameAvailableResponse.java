package promiseofblood.umpabackend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IsUsernameAvailableResponse {

  private String username;

  private boolean isAvailable;

}
