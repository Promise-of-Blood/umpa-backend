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

@Component
@RequiredArgsConstructor
public class JwtService {

  @Value("${jwt.secret:secretKeysecretKeysecretKeysecretKeysecretKey}")
  private String secretKeyValue;

  private static final long ACCESS_TOKEN_EXPIRATION = 3600000;

  private static final long REFRESH_TOKEN_EXPIRATION = 3600000 * 24 * 7;


  public String createAccessToken(Long id, String name) {
    Algorithm algorithm = Algorithm.HMAC256(secretKeyValue.getBytes());
    return JWT.create()
      .withClaim("id", id)
      .withClaim("name", name)
      .withIssuedAt(new Date(System.currentTimeMillis()))
      .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
      .sign(algorithm);
  }

  public String createRefreshToken(Long id) {
    Algorithm algorithm = Algorithm.HMAC256(secretKeyValue.getBytes());
    return JWT.create()
      .withClaim("id", id)
      .withIssuedAt(new Date(System.currentTimeMillis()))
      .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
      .sign(algorithm);
  }

  public DecodedJWT validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(secretKeyValue.getBytes());
      JWTVerifier verifier = JWT.require(algorithm).build();
      return verifier.verify(token);
    } catch (TokenExpiredException e) {
      throw new RuntimeException("Expired JWT token: " + e.getMessage());
    } catch (JWTVerificationException e) {
      throw new RuntimeException("Invalid JWT token: " + e.getMessage());
    }
  }

  public Long getUserIdFromToken(String token) {
    DecodedJWT jwt = validateToken(token);
    return jwt.getClaim("id").asLong();
  }

  public boolean isTokenExpired(String token) {
    try {
      DecodedJWT jwt = validateToken(token);
      return jwt.getExpiresAt().before(new Date());
    } catch (TokenExpiredException e) {
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error checking token expiration: " + e.getMessage());
    }
  }
}
