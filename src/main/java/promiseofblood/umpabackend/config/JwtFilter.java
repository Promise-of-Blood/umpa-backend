package promiseofblood.umpabackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNullApi;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import promiseofblood.umpabackend.domain.service.JwtService;
import java.io.IOException;
import java.util.Collections;


@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    String authorization = request.getHeader("Authorization");

    //Authorization 헤더 검증
    if (authorization == null || !authorization.startsWith("Bearer ")) {
      System.out.println("token null");
      filterChain.doFilter(request, response);
      return;
    }

    System.out.println("authorization now");
    //Bearer 부분 제거 후 순수 토큰만 획득
    String token = authorization.split(" ")[1];

    System.out.println(token);

    filterChain.doFilter(request, response);
  }

}
