package promiseofblood.umpabackend.dto.response.common;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExceptionResponse {

  private final String message;

  private final LocalDateTime timestamp = LocalDateTime.now();

}
