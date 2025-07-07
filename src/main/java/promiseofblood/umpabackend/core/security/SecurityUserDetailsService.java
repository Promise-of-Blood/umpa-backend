package promiseofblood.umpabackend.core.security;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.core.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.repository.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public SecurityUserDetails loadUserByUsername(String loginId) {
    Optional<User> user = userRepository.findByLoginId(loginId);

    log.info(
      "--- SecurityUserDetailsService.loadUserByUsername() called with loginId: {} ---",
      loginId
    );

    if (user.isEmpty()) {
      throw new UnauthorizedException("인증에 실패하였습니다. 사용자를 찾을 수 없습니다.");
    }

    return SecurityUserDetails.of(user.get());
  }
}
