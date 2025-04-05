package promiseofblood.umpabackend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.context.request.WebRequest;
import promiseofblood.umpabackend.dto.response.ExceptionResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponse> handleGlobalException(Exception ex, WebRequest request) {
    log.error("서버 내부 오류가 발생했습니다: {}", ex.getMessage(), ex);

    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      "서버 내부 오류가 발생했습니다."
    );

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(ExceptionResponse);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFound(Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ExceptionResponse ExceptionResponse = new ExceptionResponse(ex.getMessage());

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(ExceptionResponse);
  }

  @ExceptionHandler(NotSupportedOauth2ProviderException.class)
  public ResponseEntity<ExceptionResponse> handleNotSupportedOauth2Provider(Exception ex,
    WebRequest request) {
    log.error(ex.getMessage(), ex);
    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      ex.getMessage()
    );

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ExceptionResponse);
  }
}
