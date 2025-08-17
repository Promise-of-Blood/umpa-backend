package promiseofblood.umpabackend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ApiResponse {

  @Getter
  @AllArgsConstructor
  public static class ExceptionResponse {

    private final String message;

    private final LocalDateTime timestamp = LocalDateTime.now();

  }
}
