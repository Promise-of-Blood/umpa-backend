package promiseofblood.umpabackend.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.request.GeneralRegisterRequest;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;

  public RegisterCompleteResponse registerUser(GeneralRegisterRequest generalRegisterRequest) {

    User user = User.builder()
      .loginId(generalRegisterRequest.getLoginId())
      .password(passwordEncoder.encode(generalRegisterRequest.getPassword()))
      .role(promiseofblood.umpabackend.domain.vo.Role.USER)
      .build();
    user = userRepository.save(user);

    JwtPairDto jwtPairDto = JwtPairDto.builder()
      .accessToken(jwtService.createAccessToken(user.getId()))
      .refreshToken(jwtService.createRefreshToken(user.getId()))
      .build();

    return RegisterCompleteResponse.builder()
      .user(UserDto.ofInitialUser(user))
      .jwtPair(jwtPairDto)
      .build();
  }

  public List<UserDto> getUsers() {

    return userRepository.findAll()
      .stream()
      .map(UserDto::ofInitialUser)
      .toList();
  }

  public void deleteUsers() {

    userRepository.deleteAll();
  }

  public JwtPairDto generateJwt(String loginId, String password) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    return jwtService.createJwtPair(user.getId());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    return null;
  }

}
