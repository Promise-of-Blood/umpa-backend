package promiseofblood.umpabackend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import promiseofblood.umpabackend.domain.service.JwtService;
import java.io.IOException;
import promiseofblood.umpabackend.domain.vo.Role;


@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

  public static final String AUTH_HEADER_PREFIX = "Bearer ";
  public static final String AUTH_HEADER_NAME = "Authorization";

  private final JwtService jwtService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {

    String jwt = this.getTokenFromRequest(request);
    if (jwt == null) {
      filterChain.doFilter(request, response);
      return;
    }

    if (!jwtService.isValidJwt(jwt)) {
      filterChain.doFilter(request, response);
      return;
    }

    Long userId = jwtService.getUserIdFromToken(jwt);
    Role role = jwtService.getRoleFromToken(jwt);

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
      userId,
      null,
      List.of(new SimpleGrantedAuthority(role.name()))
    );
    SecurityContextHolder.getContext().setAuthentication(auth);
    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTH_HEADER_NAME);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTH_HEADER_PREFIX)) {
      return bearerToken.substring(7);
    }

    return null;
  }

}
