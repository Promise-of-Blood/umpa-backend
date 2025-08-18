package promiseofblood.umpabackend.core.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import promiseofblood.umpabackend.dto.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * 전역 예외 처리 핸들러 (500 Internal Server Error)
   *
   * <p>- 모든 예외를 처리하며, 서버 내부 오류가 발생했을 때 호출됩니다.
   */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleGlobalException(
      Exception ex, WebRequest request) {
    log.error("서버 내부 오류가 발생했습니다: {}", ex.getMessage(), ex);

    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse("서버 내부 오류가 발생했습니다.");

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
  }

  /**
   * 인증 관련 예외 처리 핸들러 (401 Unauthorized)
   *
   * <p>- 인증 실패 시 호출됩니다.
   */
  @ExceptionHandler(InsufficientAuthenticationException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleAuthenticationException(
      Exception ex, WebRequest request) {
    log.error("인증 실패: {}", ex.getMessage(), ex);
    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse("인증에 실패하였습니다.");

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleUnauthorizedException(
      Exception ex, WebRequest request) {
    log.error("인증 실패: {}", ex.getMessage(), ex);
    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse(ex.getMessage());

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
  }

  @ExceptionHandler(TokenExpiredException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleTokenExpiredException(
      Exception ex, WebRequest request) {
    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse("토큰이 만료되었습니다.");

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
  }

  @ExceptionHandler(JWTDecodeException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleJWTDecodeException(
      Exception ex, WebRequest request) {

    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse("올바르지 않은 토큰입니다.");

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exceptionResponse);
  }

  /** 유효성 검사 실패 예외 처리 핸들러 (400 Bad Request) */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex, WebRequest request) {
    log.error("유효성 검사 실패: {}", ex.getMessage(), ex);
    BindingResult bindingResult = ex.getBindingResult();

    StringBuilder builder = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      builder.append("[");
      builder.append(fieldError.getField());
      builder.append("](은)는 ");
      builder.append(fieldError.getDefaultMessage());
      builder.append(" 입력된 값: [");
      builder.append(fieldError.getRejectedValue());
      builder.append("]");
    }

    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse("유효성 검사 실패: " + builder);

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
  }

  // 404
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleNotFoundException(
      Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse(ex.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
  }

  @ExceptionHandler(NotSupportedOauth2ProviderException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleNotSupportedOauth2Provider(
      Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse(ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
  }

  @ExceptionHandler(RegistrationException.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleRegistrationException(
      Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse(ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
  }

  @ExceptionHandler(Oauth2UserAlreadyExists.class)
  public ResponseEntity<ApiResponse.ExceptionResponse> handleOauth2UserAlreadyExists(
      Exception ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    ApiResponse.ExceptionResponse exceptionResponse =
        new ApiResponse.ExceptionResponse(ex.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionResponse);
  }
}
