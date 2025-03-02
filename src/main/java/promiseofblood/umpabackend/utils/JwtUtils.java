package promiseofblood.umpabackend.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

  public static String createAccessToken(Long id, String name) {
    String secretKey = "secretKeysecretKeysecretKeysecretKeysecretKey";
    Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

    return Jwts.builder()
            .claim("id", id)
            .claim("name", name)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 3600000))
            .signWith(key)
            .compact();
  }
}
