package promiseofblood.umpabackend.domain.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
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
    Key key = Keys.hmacShaKeyFor(secretKeyValue.getBytes());

    return Jwts.builder()
      .claim("id", id)
      .claim("name", name)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
      .signWith(key)
      .compact();
  }

  public String createRefreshToken(Long id) {
    Key key = Keys.hmacShaKeyFor(secretKeyValue.getBytes());

    return Jwts.builder()
      .claim("id", id)
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
      .signWith(key)
      .compact();
  }

  public Claims validateToken(String token) {
    try {
      Key key = Keys.hmacShaKeyFor(secretKeyValue.getBytes());
      return Jwts.parser()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody();
    } catch (
      SignatureException |
      MalformedJwtException |
      ExpiredJwtException |
      UnsupportedJwtException |
      IllegalArgumentException e
    ) {
      throw new RuntimeException("Invalid JWT token: " + e.getMessage());
    }
  }

  public Long getUserIdFromToken(String token) {
    Claims claims = validateToken(token);
    return claims.get("id", Long.class);
  }

  public boolean isTokenExpired(String token) {
    try {
      Claims claims = validateToken(token);
      return claims.getExpiration().before(new Date());
    } catch (ExpiredJwtException e) {
      return true;
    } catch (Exception e) {
      throw new RuntimeException("Error checking token expiration: " + e.getMessage());
    }
  }
}
