package promiseofblood.umpabackend.core.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.core.security.JwtFilter;

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
        .requestMatchers("/api/v1/constants/**").permitAll()
        .requestMatchers("/api/v1/users/**").permitAll() // TODO: 인가 처리하기
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

  @Bean
  static RoleHierarchy roleHierarchy() {
    return RoleHierarchyImpl.withDefaultRolePrefix()
      .role("ADMIN").implies(Role.STAFF.name())
      .role("STAFF").implies(Role.USER.name())
      .build();
  }

  @Bean
  static MethodSecurityExpressionHandler methodSecurityExpressionHandler(
    RoleHierarchy roleHierarchy) {
    DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy);
    return expressionHandler;
  }
}
