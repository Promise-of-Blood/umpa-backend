package promiseofblood.umpabackend.domain.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.dto.JwtPairDto;

@Component
@RequiredArgsConstructor
public class JwtService {

  @Value("${jwt.secret}")
  String secretKeyString;

  @Value("${jwt.access-token-expiration}")
  long accessTokenExpiration;

  @Value("${jwt.refresh-token-expiration}")
  long refreshTokenExpiration;

  public JwtPairDto createJwtPair(Long id) {

    String accessToken = createAccessToken(id);
    String refreshToken = createRefreshToken(id);

    return JwtPairDto.builder()
      .accessToken(accessToken)
      .refreshToken(refreshToken)
      .build();
  }

  public String createAccessToken(Long id) {

    return createJwt("access", id, Role.USER, accessTokenExpiration);
  }

  public String createRefreshToken(Long id) {

    return createJwt("refresh", id, Role.USER, refreshTokenExpiration);
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

  public Role getRoleFromToken(String token) {
    DecodedJWT jwt = this.decodeJwt(token);
    return Role.valueOf(jwt.getClaim("role").asString());
  }

  public boolean isTokenExpired(String token) {
    try {
      DecodedJWT jwt = this.decodeJwt(token);
      return jwt.getExpiresAt().before(new Date());
    } catch (TokenExpiredException e) {
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error checking token expiration: " + e.getMessage());
    }
  }

  private Algorithm jwtAlgorithm() {

    return Algorithm.HMAC256(secretKeyString.getBytes());
  }

  private String createJwt(
    String type, Long id, Role role, long expiration) {

    return JWT.create()
      .withClaim("type", type)
      .withClaim("id", id)
      .withClaim("role", role.name())
      .withIssuedAt(new Date(System.currentTimeMillis()))
      .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
      .sign(this.jwtAlgorithm());
  }

}
