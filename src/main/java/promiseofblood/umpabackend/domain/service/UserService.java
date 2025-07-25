package promiseofblood.umpabackend.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.core.exception.RegistrationException;
import promiseofblood.umpabackend.core.exception.UnauthorizedException;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.entity.TeacherCareer;
import promiseofblood.umpabackend.domain.entity.TeacherLink;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.vo.Role;
import promiseofblood.umpabackend.domain.vo.Status;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.StudentProfileDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.request.DefaultProfileRequest;
import promiseofblood.umpabackend.dto.request.GeneralRegisterRequest;
import promiseofblood.umpabackend.dto.request.StudentProfileRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest.TeacherCareerRequest;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;
import promiseofblood.umpabackend.dto.response.TeacherProfileResponse;
import promiseofblood.umpabackend.repository.UserRepository;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final StorageService storageService;

  public RegisterCompleteResponse registerUser(GeneralRegisterRequest generalRegisterRequest) {
    if (this.isLoginIdAvailable(generalRegisterRequest.getLoginId())) {

      throw new RegistrationException("이미 사용 중인 로그인ID 입니다.");
    }

    User user = User.builder()
      .loginId(generalRegisterRequest.getLoginId())
      .password(passwordEncoder.encode(generalRegisterRequest.getPassword()))
      .status(Status.ACTIVE)
      .role(Role.USER)
      .username("임의의사용자" + System.currentTimeMillis())
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
  public List<UserDto.ProfileResponse> getUsers() {

    return userRepository.findAll()
      .stream()
      .map(UserDto.ProfileResponse::from)
      .toList();
  }

  public UserDto.ProfileResponse getUserByLoginId(String loginId) {

    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    return UserDto.ProfileResponse.from(user);
  }

  public UserDto.ProfileResponse patchDefaultProfile(
    String loginId, DefaultProfileRequest defaultProfileRequest) {
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

    return UserDto.ProfileResponse.from(updatedUser);
  }

  @Transactional
  public TeacherProfileResponse patchTeacherProfile(
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
        .description(teacherProfileRequest.getDescription())
        .major(teacherProfileRequest.getMajor())
        .lessonRegion(teacherProfileRequest.getLessonRegion())
        .careers(teacherCareers)
        .links(teacherLinks)
        .build();
      user.patchTeacherProfile(teacherProfile);
    } else {
      teacherProfile.setDescription(teacherProfileRequest.getDescription());
      teacherProfile.setMajor(teacherProfileRequest.getMajor());
      teacherProfile.setLessonRegion(teacherProfileRequest.getLessonRegion());
      teacherProfile.getCareers().clear();
      teacherProfile.getCareers().addAll(teacherCareers);
      for (TeacherCareer career : teacherCareers) {
        career.setTeacherProfile(teacherProfile);
      }

      teacherProfile.getLinks().clear();
      teacherProfile.getLinks().addAll(teacherLinks);
      for (TeacherLink link : teacherLinks) {
        link.setTeacherProfile(teacherProfile);
      }

      user.patchTeacherProfile(teacherProfile);
    }
    user = userRepository.save(user);

    return TeacherProfileResponse.from(user.getTeacherProfile());
  }

  @Transactional
  public StudentProfileDto patchStudentProfile(
    String loginId, StudentProfileRequest studentProfileRequest
  ) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    StudentProfile studentProfile = user.getStudentProfile();

    if (studentProfile == null) {
      studentProfile = StudentProfile.builder()
        .major(studentProfileRequest.getMajor())
        .lessonStyle(studentProfileRequest.getLessonStyles().get(0))
        .preferredColleges(studentProfileRequest.getPreferredColleges())
        .grade(studentProfileRequest.getGrade())
        .subject(studentProfileRequest.getSubject())
        .lessonRequirements(studentProfileRequest.getLessonRequirements())
        .build();
      user.patchStudentProfile(studentProfile);
    } else {
      studentProfile.setMajor(studentProfileRequest.getMajor());
      studentProfile.setLessonStyle(studentProfileRequest.getLessonStyles().get(0));
      studentProfile.setPreferredColleges(studentProfileRequest.getPreferredColleges());
      studentProfile.setGrade(studentProfileRequest.getGrade());
      studentProfile.setSubject(studentProfileRequest.getSubject());
      studentProfile.setLessonRequirements(studentProfileRequest.getLessonRequirements());
      user.patchStudentProfile(studentProfile);
    }
    userRepository.save(user);

    return StudentProfileDto.of(studentProfile);
  }

  public JwtPairDto generateGeneralJwt(String loginId, String password) {

    Optional<User> optionalUser = userRepository.findByLoginId(loginId);

    boolean userExists = optionalUser.isPresent();
    boolean isPasswordCorrect = optionalUser
      .map(user -> passwordEncoder.matches(password, user.getPassword()))
      .orElse(false);

    if (!userExists || !isPasswordCorrect) {
      throw new UnauthorizedException("사용자를 찾을 수 없습니다. 또는 비밀번호가 일치하지 않습니다.");
    }

    return jwtService.createJwtPair(optionalUser.get().getId(), optionalUser.get().getLoginId());
  }

  @Transactional
  public JwtPairDto refreshToken(String refreshToken) {
    Long userId = jwtService.getUserIdFromToken(refreshToken);

    User user = userRepository.findById(userId)
      .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    String newAccessToken = jwtService.createAccessToken(user.getId(), user.getLoginId());
    String newRefreshToken = jwtService.createRefreshToken(user.getId(), user.getLoginId());

    return JwtPairDto.builder()
      .accessToken(newAccessToken)
      .refreshToken(newRefreshToken)
      .build();
  }

  public boolean isUsernameAvailable(String username) {

    return !userRepository.existsByUsername(username);
  }


  private boolean isLoginIdAvailable(String loginId) {

    return userRepository.existsByLoginId(loginId);
  }


}
