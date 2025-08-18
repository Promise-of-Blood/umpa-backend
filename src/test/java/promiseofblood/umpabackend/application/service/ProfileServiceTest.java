package promiseofblood.umpabackend.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.ProfileType;
import promiseofblood.umpabackend.dto.UserDto.DefaultProfilePatchRequest;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private ProfileService subject;

  @Test
  void patchDefaultProfile_whenUsernameIsInvalid() {
    // Given
    User user = User.builder()
      .username("name")
      .profileType(ProfileType.STUDENT)
      .build();

    when(userRepository.findByLoginId(any())).thenReturn(Optional.of(user));
    when(userRepository.save(user)).thenReturn(user);

    DefaultProfilePatchRequest defaultProfilePatchRequest = new DefaultProfilePatchRequest(
      // 긴 닉네임
      "ABCABCABC",
      null,
      null,
      null
    );

    // When
    // Then
    assertThrows(
      Exception.class,
      () -> subject.patchDefaultProfile(any(), defaultProfilePatchRequest)
    );
  }
}