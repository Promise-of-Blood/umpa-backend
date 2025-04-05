package promiseofblood.umpabackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import promiseofblood.umpabackend.domain.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final UserService userService;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.formLogin(
      AbstractHttpConfigurer::disable
    );
    http.logout(
      AbstractHttpConfigurer::disable
    );
    http.sessionManagement(session -> session
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    );
    http.csrf(
      AbstractHttpConfigurer::disable
    );
    http.authorizeHttpRequests(
      (authorizeRequests) -> authorizeRequests
        .anyRequest().permitAll()
    );
    http.addFilterBefore(
      new JwtFilter(userService, "123123123123123123112312312312312312311231231231231231231"),
      UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }
}
