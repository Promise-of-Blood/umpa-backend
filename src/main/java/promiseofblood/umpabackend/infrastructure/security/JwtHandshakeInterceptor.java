package promiseofblood.umpabackend.infrastructure.security;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import promiseofblood.umpabackend.application.service.JwtService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

  private final JwtService jwtService;

  @Override
  public boolean beforeHandshake(
      ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Map<String, Object> attributes)
      throws Exception {

    if (request instanceof ServletServerHttpRequest servletRequest) {
      String token = extractToken(servletRequest);

      if (token != null && jwtService.validateToken(token)) {
        String loginId = jwtService.getLoginIdFromToken(token);
        attributes.put("loginId", loginId);
        attributes.put("token", token);
        log.info("WebSocket handshake successful for user: {}", loginId);
        return true;
      }
    }

    log.warn("WebSocket handshake failed: Invalid or missing token");
    return false;
  }

  @Override
  public void afterHandshake(
      ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Exception exception) {
    // No action needed after handshake
  }

  private String extractToken(ServletServerHttpRequest request) {
    // Try to get token from query parameter first (for mobile clients)
    String token = request.getServletRequest().getParameter("token");

    // If not in query param, try Authorization header
    if (token == null) {
      String authHeader = request.getServletRequest().getHeader("Authorization");
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
      }
    }

    return token;
  }
}
