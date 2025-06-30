package promiseofblood.umpabackend.core.security;

import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import promiseofblood.umpabackend.domain.entity.User;

@RequiredArgsConstructor
public class SecurityUserDetails implements UserDetails {

  @Getter
  private final User user;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
  }

  @Override
  public String getPassword() {

    return user.getPassword();
  }

  @Override
  public String getUsername() {

    return user.getLoginId();
  }

  @Override
  public String toString() {
    return "SecurityUserDetails{" +
      "loginId='" + user.getLoginId() + '\'' +
      '}';
  }

}
