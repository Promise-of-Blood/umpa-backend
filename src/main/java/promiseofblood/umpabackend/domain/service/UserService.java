package promiseofblood.umpabackend.domain.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.TeacherProfileDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.request.DefaultProfileRequest;
import promiseofblood.umpabackend.dto.request.GeneralRegisterRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest.TeacherCareerRequest;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final StorageService storageService;

  public RegisterCompleteResponse registerUser(GeneralRegisterRequest generalRegisterRequest) {

    User user = User.builder()
      .loginId(generalRegisterRequest.getLoginId())
      .password(passwordEncoder.encode(generalRegisterRequest.getPassword()))
      .role(Role.USER)
      .build();
    user = userRepository.save(user);

    JwtPairDto jwtPairDto = JwtPairDto.builder()
      .accessToken(jwtService.createAccessToken(user.getId(), user.getLoginId()))
      .refreshToken(jwtService.createRefreshToken(user.getId(), user.getLoginId()))
      .build();

    return RegisterCompleteResponse.builder()
      .user(UserDto.of(user))
      .jwtPair(jwtPairDto)
      .build();
  }

  /**
   * 사용자 목록을 조회합니다.
   *
   * @return 사용자 목록
   */
  public List<UserDto> getUsers() {

    return userRepository.findAll()
      .stream()
      .map(UserDto::of)
      .toList();
  }

  public void deleteUsers() {

    userRepository.deleteAll();
  }

  public UserDto patchDefaultProfile(String loginId, DefaultProfileRequest defaultProfileRequest) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    if (defaultProfileRequest.getUsername() != null) {
      user.patchUsername(defaultProfileRequest.getUsername());
    }
    if (defaultProfileRequest.getGender() != null) {
      user.patchGender(defaultProfileRequest.getGender());
    }
    if (defaultProfileRequest.getProfileImage() != null) {
      String storedFilePath = storageService.store(
        defaultProfileRequest.getProfileImage(),
        "users",
        user.getId().toString(),
        "default-profile"
      );
      user.patchProfileImageUrl(storedFilePath);
    }
    User updatedUser = userRepository.save(user);

    return UserDto.of(updatedUser);
  }

  @Transactional
  public TeacherProfileDto patchTeacherProfile(
    String loginId, TeacherProfileRequest teacherProfileRequest
  ) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    TeacherProfile teacherProfile = user.getTeacherProfile();

    List<TeacherCareer> teacherCareers = new ArrayList<>(List.of());
    for (TeacherCareerRequest teacherCareerRequest : teacherProfileRequest.getCareers()) {
      TeacherCareer newCareer = TeacherCareer.builder()
        .title(teacherCareerRequest.getTitle())
        .isRepresentative(teacherCareerRequest.isRepresentative())
        .start(teacherCareerRequest.getStartDate())
        .end(teacherCareerRequest.getEndDate())
        .build();
      teacherCareers.add(newCareer);
    }

    List<TeacherLink> teacherLinks = new ArrayList<>(List.of());
    for (String teacherLink : teacherProfileRequest.getLinks()) {
      TeacherLink newLink = TeacherLink.builder()
        .link(teacherLink)
        .build();
      teacherLinks.add(newLink);
    }

    if (teacherProfile == null) {
      teacherProfile = TeacherProfile.builder()
        .major(teacherProfileRequest.getMajor())
        .lessonRegion(teacherProfileRequest.getLessonRegion())
        .careers(teacherCareers)
        .links(teacherLinks)
        .build();
      user.patchTeacherProfile(teacherProfile);
    } else {
      teacherProfile.setMajor(teacherProfileRequest.getMajor());
      teacherProfile.setLessonRegion(teacherProfileRequest.getLessonRegion());
      teacherProfile.getCareers().clear();
      teacherProfile.getCareers().addAll(teacherCareers);
      teacherProfile.getLinks().clear();
      teacherProfile.getLinks().addAll(teacherLinks);
    }

    return TeacherProfileDto.of(user.getTeacherProfile());
  }

  public JwtPairDto generateJwt(String loginId, String password) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
    }

    return jwtService.createJwtPair(user.getId(), user.getLoginId());
  }
}
