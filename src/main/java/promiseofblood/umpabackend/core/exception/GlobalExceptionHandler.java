package promiseofblood.umpabackend.core.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import promiseofblood.umpabackend.dto.response.common.ExceptionResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 전역 예외 처리 핸들러 (500 Internal Server Error)
   * <p>
   * - 모든 예외를 처리하며, 서버 내부 오류가 발생했을 때 호출됩니다.
   */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponse> handleGlobalException(
    Exception ex, WebRequest request
  ) {
    log.error("서버 내부 오류가 발생했습니다: {}", ex.getMessage(), ex);

    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      "서버 내부 오류가 발생했습니다."
    );

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body(ExceptionResponse);
  }

  /**
   * 인증 관련 예외 처리 핸들러 (401 Unauthorized)
   * <p>
   * - 인증 실패 시 호출됩니다.
   */
  @ExceptionHandler(InsufficientAuthenticationException.class)
  public ResponseEntity<ExceptionResponse> handleAuthenticationException(
    Exception ex,
    WebRequest request
  ) {
    log.error("인증 실패: {}", ex.getMessage(), ex);
    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      "인증에 실패하였습니다."
    );

    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(ExceptionResponse);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ExceptionResponse> handleUnauthorizedException(
    Exception ex,
    WebRequest request
  ) {
    log.error("인증 실패: {}", ex.getMessage(), ex);
    ExceptionResponse ExceptionResponse = new ExceptionResponse(ex.getMessage());

    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(ExceptionResponse);
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ExceptionResponse> handleTokenExpiredException(
    Exception ex,
    WebRequest request
  ) {
    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      "토큰이 만료되었습니다."
    );

    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(ExceptionResponse);
  }

  @ExceptionHandler(JWTDecodeException.class)
  public ResponseEntity<ExceptionResponse> handleJWTDecodeException(
    Exception ex,
    WebRequest request
  ) {
    
    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      "올바르지 않은 토큰입니다."
    );

    return ResponseEntity
      .status(HttpStatus.UNAUTHORIZED)
      .body(ExceptionResponse);
  }

  // 404
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFoundException(
    Exception ex,
    WebRequest request
  ) {
    log.error(ex.getMessage(), ex);
    ExceptionResponse ExceptionResponse = new ExceptionResponse(ex.getMessage());

    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .body(ExceptionResponse);
  }

  @ExceptionHandler(NotSupportedOauth2ProviderException.class)
  public ResponseEntity<ExceptionResponse> handleNotSupportedOauth2Provider(
    Exception ex,
    WebRequest request
  ) {
    log.error(ex.getMessage(), ex);
    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      ex.getMessage()
    );

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ExceptionResponse);
  }

  @ExceptionHandler(Oauth2UserAlreadyExists.class)
  public ResponseEntity<ExceptionResponse> handleOauth2UserAlreadyExists(
    Exception ex,
    WebRequest request
  ) {
    log.error(ex.getMessage(), ex);
    ExceptionResponse ExceptionResponse = new ExceptionResponse(
      ex.getMessage()
    );

    return ResponseEntity
      .status(HttpStatus.BAD_REQUEST)
      .body(ExceptionResponse);
  }

}
