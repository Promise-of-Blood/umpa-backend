package kr.co.umpabackend.infrastructure.security;

import java.util.Optional;
import kr.co.umpabackend.application.exception.UnauthorizedException;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public SecurityUserDetails loadUserByUsername(String loginId) {
    Optional<User> user = userRepository.findByLoginId(loginId);

    if (user.isEmpty()) {
      throw new UnauthorizedException("인증에 실패하였습니다. 사용자를 찾을 수 없습니다.");
    }

    return SecurityUserDetails.of(user.get());
  }
}
