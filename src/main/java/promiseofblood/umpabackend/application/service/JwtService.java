package promiseofblood.umpabackend.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.infrastructure.config.JwtConfig;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

  private final JwtConfig jwtConfig;

  public boolean validateToken(String token) {
    try {
      verifyJwt(token);
      return true;
    } catch (Exception e) {
      log.error("Invalid JWT token: {}", e.getMessage());
      return false;
    }
  }

  public String createAccessToken(Long id, String loginId) {

    return createJwt("access", id, loginId, jwtConfig.getAccessTokenExpiration());
  }

  public String createRefreshToken(Long id, String loginId) {

    return createJwt("refresh", id, loginId, jwtConfig.getRefreshTokenExpiration());
  }

  public void verifyJwt(String token) {
    JWTVerifier verifier = JWT.require(this.jwtAlgorithm()).build();
    verifier.verify(token);
  }

  public String getTypeFromToken(String token) {
    DecodedJWT jwt = this.decodeJwt(token);
    return jwt.getClaim("type").asString();
  }

  public Long getUserIdFromToken(String token) {
    DecodedJWT jwt = this.decodeJwt(token);
    return jwt.getClaim("id").asLong();
  }

  public String getLoginIdFromToken(String token) {
    DecodedJWT jwt = this.decodeJwt(token);
    return jwt.getClaim("loginId").asString();
  }

  private DecodedJWT decodeJwt(String jwt) {
    try {
      JWTVerifier verifier = JWT.require(this.jwtAlgorithm()).build();
      return verifier.verify(jwt);
    } catch (TokenExpiredException e) {
      throw new RuntimeException("Expired JWT token: " + e.getMessage());
    } catch (JWTVerificationException e) {
      throw new RuntimeException("Invalid JWT token: " + e.getMessage());
    }
  }

  private Algorithm jwtAlgorithm() {

    return Algorithm.HMAC256(jwtConfig.getSecretKey().getBytes());
  }

  private String createJwt(String type, Long id, String loginId, long expiration) {

    return JWT.create()
        .withClaim("type", type)
        .withClaim("id", id)
        .withClaim("loginId", loginId)
        .withIssuedAt(new Date(System.currentTimeMillis()))
        .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
        .sign(this.jwtAlgorithm());
  }
}
