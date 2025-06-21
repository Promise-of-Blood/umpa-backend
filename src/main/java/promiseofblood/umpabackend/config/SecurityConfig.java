package promiseofblood.umpabackend.config;

import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtFilter jwtFilter;

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
        .requestMatchers("/api/docs/**").permitAll()
        .requestMatchers("/api/swagger-ui/**").permitAll()
        .requestMatchers("/api/v1/users/**").permitAll()
        .requestMatchers("/api/v1/users/register/**").permitAll()
        .requestMatchers("/api/v1/users/token/**").permitAll()
        .anyRequest().authenticated()
    );
    http.addFilterBefore(
      jwtFilter, UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
