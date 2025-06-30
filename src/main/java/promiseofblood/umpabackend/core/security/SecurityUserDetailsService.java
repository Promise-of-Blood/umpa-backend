package promiseofblood.umpabackend.core.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public SecurityUserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByLoginId(loginId);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
    }

    return new SecurityUserDetails(user.get());
  }
}
