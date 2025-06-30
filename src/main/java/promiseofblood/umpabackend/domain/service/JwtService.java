package promiseofblood.umpabackend.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.core.config.JwtConfig;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.dto.JwtPairDto;

@Component
@RequiredArgsConstructor
public class JwtService {

  private final JwtConfig jwtConfig;

  public JwtPairDto createJwtPair(Long id, String loginId) {

    String accessToken = createAccessToken(id, loginId);
    String refreshToken = createRefreshToken(id, loginId);

    return JwtPairDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
  }

  public String createAccessToken(Long id, String loginId) {

    return createJwt("access", id, loginId, Role.USER, jwtConfig.getAccessTokenExpiration());
  }

  public String createRefreshToken(Long id, String loginId) {

    return createJwt("refresh", id, loginId, Role.USER, jwtConfig.getRefreshTokenExpiration());
  }

  public boolean isValidJwt(String token) {
    try {
      JWTVerifier verifier = JWT.require(this.jwtAlgorithm()).build();
      verifier.verify(token);
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
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

  private String createJwt(String type, Long id, String loginId, Role role, long expiration) {

    return JWT.create().withClaim("type", type).withClaim("loginId", loginId).withClaim("id", id)
      .withClaim("username", "정재균").withClaim("role", role.name())
      .withIssuedAt(new Date(System.currentTimeMillis()))
      .withExpiresAt(new Date(System.currentTimeMillis() + expiration)).sign(this.jwtAlgorithm());
  }

}
