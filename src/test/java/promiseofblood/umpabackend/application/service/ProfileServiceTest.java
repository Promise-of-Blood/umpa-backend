//package promiseofblood.umpabackend.application.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import jakarta.validation.ConstraintViolation;
//import jakarta.validation.Validation;
//import jakarta.validation.Validator;
//import jakarta.validation.ValidatorFactory;
//import java.util.Optional;
//import java.util.Set;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import promiseofblood.umpabackend.domain.entity.User;
//import promiseofblood.umpabackend.domain.repository.UserRepository;
//import promiseofblood.umpabackend.domain.vo.Gender;
//import promiseofblood.umpabackend.domain.vo.ProfileType;
//import promiseofblood.umpabackend.dto.UserDto.DefaultProfilePatchRequest;
//
//@ExtendWith(MockitoExtension.class)
//class ProfileServiceTest {
//
//  @Mock
//  private UserRepository userRepository;
//
//  @Mock
//  private StorageService storageService;
//
//  @InjectMocks
//  private ProfileService subject;
//
//  private Validator validator;
//
//  @BeforeEach
//  void setUp() {
//    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//    validator = factory.getValidator();
//  }
//
//  @Test
//  @DisplayName("사용자 닉네임이 8자를 초과하면 검증 오류가 발생해야 한다")
//  void validateUsername_whenExceedsMaxLength_shouldFailValidation() {
//    // Given
//    DefaultProfilePatchRequest requestWithLongUsername = new DefaultProfilePatchRequest(
//      "닉네임이너무길어요", // 8자 초과 닉네임
//      Gender.MALE,
//      ProfileType.STUDENT,
//      null
//    );
//
//    // When
//    Set<ConstraintViolation<DefaultProfilePatchRequest>> violations = validator.validate(
//      requestWithLongUsername);
//
//    // Then
//    assertEquals(1, violations.size());
//    assertEquals("사용자 닉네임은 최대 8자까지 가능합니다.", violations.iterator().next().getMessage());
//  }
//
//  @Test
//  @DisplayName("사용자 닉네임이 8자 이하면 검증에 성공해야 한다")
//  void validateUsername_whenWithinMaxLength_shouldPassValidation() {
//    // Given
//    DefaultProfilePatchRequest requestWithValidUsername = new DefaultProfilePatchRequest(
//      "홍길동", // 8자 이하 닉네임
//      Gender.MALE,
//      ProfileType.STUDENT,
//      null
//    );
//
//    // When
//    Set<ConstraintViolation<DefaultProfilePatchRequest>> violations = validator.validate(
//      requestWithValidUsername);
//
//    // Then
//    assertEquals(0, violations.size());
//  }
//
//  @Test
//  @DisplayName("정확히 8자의 닉네임은 검증에 성공해야 한다")
//  void validateUsername_whenExactlyMaxLength_shouldPassValidation() {
//    // Given
//    DefaultProfilePatchRequest requestWithBorderlineUsername = new DefaultProfilePatchRequest(
//      "12345678", // 정확히 8자 닉네임
//      Gender.MALE,
//      ProfileType.STUDENT,
//      null
//    );
//
//    // When
//    Set<ConstraintViolation<DefaultProfilePatchRequest>> violations = validator.validate(
//      requestWithBorderlineUsername);
//
//    // Then
//    assertEquals(0, violations.size());
//  }
//
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
//}