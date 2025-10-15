package promiseofblood.umpabackend.infrastructure.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import promiseofblood.umpabackend.application.service.JwtService;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor =
        MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

    if (StompCommand.CONNECT.equals(accessor.getCommand())) {
      String jwt = accessor.getFirstNativeHeader("Authorization");
      log.error("CONNECT jwt: {}", jwt);

      if (jwt != null) {
        if (jwtService.validateToken(jwt)) {
          String loginId = jwtService.getLoginIdFromToken(jwt);
          UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);
          Authentication authentication =
              new UsernamePasswordAuthenticationToken(
                  userDetails, "", userDetails.getAuthorities());
          accessor.getSessionAttributes().put("userAuthentication", authentication);
          accessor.setUser(authentication);
          log.error("Set user: {}", authentication.getName());
        }
      }
    }
    return message;
  }
}
