package promiseofblood.umpabackend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import promiseofblood.umpabackend.domain.service.UserService;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final UserService userService;
  private final String secretKey;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {
    final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

    logger.info("authorization = " + authorization);

    if (authorization == null || !authorization.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // Token 꺼내기
    String token = authorization.split(" ")[1];

    // Token Expired 되었는지 여부
//    if (JwtUtils.isExpired(token, secretKey)) {
//      logger.error("Token 이 만료되었습니다.");
//      filterChain.doFilter(request, response);
//      return;
//    }

    // UserName Token에서 꺼내기
    String userName = "";

    // 권한 부여
    UsernamePasswordAuthenticationToken authenticationToken =
      new UsernamePasswordAuthenticationToken(userName, null,
        List.of(new SimpleGrantedAuthority("USER")));

    // Detail을 넣어준다.
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }
}
