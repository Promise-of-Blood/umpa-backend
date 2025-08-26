package promiseofblood.umpabackend.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.Gender;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.ProfileType;
import promiseofblood.umpabackend.domain.vo.UserStatus;
import promiseofblood.umpabackend.domain.vo.Username;
import promiseofblood.umpabackend.dto.StudentProfileDto.StudentProfileRequest;
import promiseofblood.umpabackend.dto.TeacherProfileDto.TeacherProfileRequest;
import promiseofblood.umpabackend.dto.UserDto.DefaultProfilePatchRequest;
import promiseofblood.umpabackend.dto.UserDto.ProfileResponse;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private StorageService storageService;

  @InjectMocks
  private ProfileService subject;

  private Validator validator;

  @BeforeEach
  void setUp() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  @DisplayName("사용자 닉네임이 8자를 초과하면 검증 오류가 발생해야 한다")
  void validateUsername_whenExceedsMaxLength_shouldFailValidation() {
    // Given
    DefaultProfilePatchRequest requestWithLongUsername = new DefaultProfilePatchRequest(
      "닉네임이너무길어요", // 8자 초과 닉네임
      Gender.MALE,
      ProfileType.STUDENT,
      null
    );

    // When
    Set<ConstraintViolation<DefaultProfilePatchRequest>> violations = validator.validate(
      requestWithLongUsername);

    // Then
    assertEquals(1, violations.size());
    assertEquals("사용자 닉네임은 최대 8자까지 가능합니다.", violations.iterator().next().getMessage());
  }

  @Test
  @DisplayName("사용자 닉네임이 8자 이하면 검증에 성공해야 한다")
  void validateUsername_whenWithinMaxLength_shouldPassValidation() {
    // Given
    DefaultProfilePatchRequest requestWithValidUsername = new DefaultProfilePatchRequest(
      "홍길동", // 8자 이하 닉네임
      Gender.MALE,
      ProfileType.STUDENT,
      null
    );

    // When
    Set<ConstraintViolation<DefaultProfilePatchRequest>> violations = validator.validate(
      requestWithValidUsername);

    // Then
    assertEquals(0, violations.size());
  }

  @Test
  @DisplayName("정확히 8자의 닉네임은 검증에 성공해야 한다")
  void validateUsername_whenExactlyMaxLength_shouldPassValidation() {
    // Given
    DefaultProfilePatchRequest requestWithBorderlineUsername = new DefaultProfilePatchRequest(
      "12345678", // 정확히 8자 닉네임
      Gender.MALE,
      ProfileType.STUDENT,
      null
    );

    // When
    Set<ConstraintViolation<DefaultProfilePatchRequest>> violations = validator.validate(
      requestWithBorderlineUsername);

    // Then
    assertEquals(0, violations.size());
  }

  @Test
  @DisplayName("대표 문구 수정 성공 테스트")
  void patchTeacherProfile_whenOnlyUpdateKeyphrase() {
    // Given
    String newKeyphrase = "new_keyphrase";
    final User user = createTestTeacherUser();
    final TeacherProfileRequest teacherProfileRequest = TeacherProfileRequest.builder()
      .keyphrase(newKeyphrase)
      .build();

    when(userRepository.findByLoginId(any())).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);

    // When
    ProfileResponse profileResponseDto = subject.patchTeacherProfile(any(), teacherProfileRequest);

    // Then
    assertEquals(newKeyphrase, profileResponseDto.getTeacherProfile().getKeyphrase());
  }

  @Test
  @DisplayName("처음 회원가입 시 선생님 프로필 항목:전공 입력 테스트")
  void patchTeacherProfile_withMajorOnly_whenUserHasNotTeacherProfile() {
    // Given
    Major newMajor = Major.BASS;
    final User user = createTestPendingUser();
    user.patchDefaultProfile(null, null, null, ProfileType.TEACHER);
    final TeacherProfileRequest teacherProfileRequest = TeacherProfileRequest.builder()
      .major(newMajor)
      .build();

    when(userRepository.findByLoginId(any())).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);

    // When
    ProfileResponse profileResponseDto = subject.patchTeacherProfile(any(), teacherProfileRequest);

    // Then
    assertEquals(newMajor.name(), profileResponseDto.getTeacherProfile().getMajor().getCode());
  }

  @Test
  @DisplayName("처음 회원가입 시 학생 프로필 항목:전공 입력 테스트")
  void patchStudentProfile_withMajorOnly_whenUserHasNotStudentProfile() {
    // Given
    Major newMajor = Major.BASS;
    final User user = createTestPendingUser();
    user.patchDefaultProfile(null, null, null, ProfileType.STUDENT);
    final StudentProfileRequest studentProfileRequest = StudentProfileRequest.builder()
      .major(newMajor)
      .build();

    when(userRepository.findByLoginId(any())).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);

    // When
    ProfileResponse profileResponseDto = subject.patchStudentProfile(any(), studentProfileRequest);

    // Then
    assertEquals(newMajor.name(), profileResponseDto.getStudentProfile().getMajor().getCode());
  }

//  @Test
//  void patchDefaultProfile_whenUsernameIsInvalid() {
//    // Given
//    User user = User.builder()
//      .username("name")
//      .profileType(ProfileType.STUDENT)
//      .build();
//
//    when(userRepository.findByLoginId(any())).thenReturn(Optional.of(user));
//    when(userRepository.save(user)).thenReturn(user);
//
//    DefaultProfilePatchRequest defaultProfilePatchRequest = new DefaultProfilePatchRequest(
//      // 긴 닉네임
//      "ABCABCABC",
//      null,
//      null,
//      null
//    );
//
//    // When
//    // Then
//    assertThrows(
//      Exception.class,
//      () -> subject.patchDefaultProfile(any(), defaultProfilePatchRequest)
//    );
//  }

  private User createTestTeacherUser() {
    final TeacherProfile teacherProfile = new TeacherProfile();

    TeacherProfileRequest request = TeacherProfileRequest.builder()
      .keyphrase("keyphrase")
      .careers(Collections.emptyList())
      .links(Collections.emptyList())
      .description("description")
      .major(Major.BASS)
      .build();

    teacherProfile.update(request);

    User user = createTestPendingUser();
    user.patchTeacherProfile(teacherProfile);
    user.patchDefaultProfile(null, null, null, ProfileType.TEACHER);

    return user;
  }

  private User createTestPendingUser() {
    return User.builder()
      .username(new Username("username"))
      .userStatus(UserStatus.PENDING)
      .build();
  }
}