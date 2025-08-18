package promiseofblood.umpabackend.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import promiseofblood.umpabackend.core.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.service.JwtService;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

  public static final String AUTH_HEADER_PREFIX = "Bearer ";
  public static final String AUTH_HEADER_NAME = "Authorization";

  private final JwtService jwtService;
  private final SecurityUserDetailsService securityUserDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      String jwt = this.getTokenFromRequest(request);

      jwtService.verifyJwt(jwt);
      SecurityUserDetails securityUserDetails =
          securityUserDetailsService.loadUserByUsername(jwtService.getLoginIdFromToken(jwt));
      UsernamePasswordAuthenticationToken auth =
          new UsernamePasswordAuthenticationToken(
              securityUserDetails, null, securityUserDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(auth);

    } catch (Exception e) {
      request.setAttribute("exception", e);
    }

    filterChain.doFilter(request, response);
  }

  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTH_HEADER_NAME);

    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(AUTH_HEADER_PREFIX)) {
      return bearerToken.substring(7);
    }

    throw new UnauthorizedException("인증 정보가 없습니다.");
  }
}
