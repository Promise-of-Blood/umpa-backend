package promiseofblood.umpabackend.infrastructure.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.infrastructure.security.JwtAuthenticationEntryPoint;
import promiseofblood.umpabackend.infrastructure.security.JwtFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtFilter jwtFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin(AbstractHttpConfigurer::disable);
    http.logout(AbstractHttpConfigurer::disable);
    http.sessionManagement(
        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.csrf(AbstractHttpConfigurer::disable);
    http.authorizeHttpRequests(
        (authorizeRequests) ->
            authorizeRequests
                // 정적 리소스 접근 허용
                .requestMatchers("/static/**")
                .permitAll()
                .requestMatchers("/api/docs/**")
                .permitAll()
                .requestMatchers("/api/swagger-ui/**")
                .permitAll()
                // 상수 API 접근 허용
                .requestMatchers("/api/v1/constants/**")
                .permitAll()
                // 회원가입/토큰발급 API 접근 허용
                .requestMatchers("/api/v1/users/register/**")
                .permitAll()
                .requestMatchers("/api/v1/users/callback/**")
                .permitAll()
                .requestMatchers("/api/v1/users/token/**")
                .permitAll()
                .requestMatchers("/api/v1/users/oauth2-authorization-urls")
                .permitAll()
                // 서비스 API 접근 제어 - GET 요청은 모두 허용, POST/PUT/DELETE는 인증 필요
                .requestMatchers(GET, "/api/v1/services/**")
                .permitAll()
                .requestMatchers(POST, "/api/v1/services/**")
                .authenticated()
                .requestMatchers(PUT, "/api/v1/services/**")
                .authenticated()
                .requestMatchers(DELETE, "/api/v1/services/**")
                .authenticated()
                .anyRequest()
                .authenticated());
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    http.exceptionHandling(
        exceptionHandling ->
            exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint));

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }

  @Bean
  static RoleHierarchy roleHierarchy() {
    return RoleHierarchyImpl.withDefaultRolePrefix()
        .role("ADMIN")
        .implies(Role.STAFF.name())
        .role("STAFF")
        .implies(Role.USER.name())
        .build();
  }

  @Bean
  static MethodSecurityExpressionHandler methodSecurityExpressionHandler(
      RoleHierarchy roleHierarchy) {
    DefaultMethodSecurityExpressionHandler expressionHandler =
        new DefaultMethodSecurityExpressionHandler();
    expressionHandler.setRoleHierarchy(roleHierarchy);
    return expressionHandler;
  }
}
