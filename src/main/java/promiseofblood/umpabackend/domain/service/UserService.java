package promiseofblood.umpabackend.domain.service;

import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.request.DefaultProfileRequest;
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
  private final StorageService storageService;

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
      .user(UserDto.of(user))
      .jwtPair(jwtPairDto)
      .build();
  }

  public List<UserDto> getUsers() {

    return userRepository.findAll()
      .stream()
      .map(UserDto::of)
      .toList();
  }

  public void deleteUsers() {

    userRepository.deleteAll();
  }

  public UserDto getUserById(Long userId) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    return UserDto.of(user);
  }

  public UserDto patchDefaultProfile(Long userId, DefaultProfileRequest defaultProfileRequest) {
    User user = userRepository.findById(userId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    if (defaultProfileRequest.getUsername() != null) {
      user.patchUsername(defaultProfileRequest.getUsername());
    }
    if (defaultProfileRequest.getGender() != null) {
      user.patchGender(defaultProfileRequest.getGender());
    }
    if (defaultProfileRequest.getProfileImage() != null) {
      Path storedFilePath = storageService.store(defaultProfileRequest.getProfileImage());
      user.patchProfileImageUrl(storedFilePath.toString());
    }
    User updatedUser = userRepository.save(user);

    return UserDto.of(updatedUser);
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
    // username은 loginId로 사용 (일반 로그인)
    // 소셜 로그인 사용자는 loginId가 null이므로 제외됨
    return userRepository.findByLoginId(username)
      .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
  }

}
