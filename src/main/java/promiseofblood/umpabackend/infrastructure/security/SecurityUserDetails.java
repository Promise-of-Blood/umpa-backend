package promiseofblood.umpabackend.infrastructure.security;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.UserStatus;

@RequiredArgsConstructor
public class SecurityUserDetails implements UserDetails {

  @Getter private final String loginId;
  private final String password;
  private final Role role;
  private final UserStatus status;

  public static SecurityUserDetails of(User user) {
    return new SecurityUserDetails(
        user.getLoginId(), user.getPassword(), user.getRole(), user.getUserStatus());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.loginId;
  }

  public UserStatus getUserStatus() {
    return this.status;
  }

  @Override
  public String toString() {
    return "SecurityUserDetails{" + "loginId='" + this.loginId + '\'' + '}';
  }
}
