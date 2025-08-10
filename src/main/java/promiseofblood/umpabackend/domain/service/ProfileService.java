package promiseofblood.umpabackend.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.UserDto.DefaultProfilePatchRequest;
import promiseofblood.umpabackend.dto.request.StudentProfileRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;
  private final StorageService storageService;

  public UserDto.ProfileResponse patchDefaultProfile(
    String loginId, DefaultProfilePatchRequest defaultProfilePatchRequest) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    if (defaultProfilePatchRequest.getUsername() != null) {
      user.patchUsername(defaultProfilePatchRequest.getUsername());
    }
    if (defaultProfilePatchRequest.getGender() != null) {
      user.patchGender(defaultProfilePatchRequest.getGender());
    }
    if (defaultProfilePatchRequest.getProfileImage() != null) {
      String storedFilePath = this.uploadProfileImage(
        user.getLoginId(),
        defaultProfilePatchRequest.getProfileImage()
      );
      user.patchProfileImageUrl(storedFilePath);
    }
    if (defaultProfilePatchRequest.getProfileType() != null) {
      user.patchProfileType(defaultProfilePatchRequest.getProfileType());
    }
    User updatedUser = userRepository.save(user);

    return UserDto.ProfileResponse.from(updatedUser);
  }


  @Transactional
  public UserDto.ProfileResponse patchTeacherProfile(
    String loginId, TeacherProfileRequest teacherProfileRequest
  ) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    TeacherProfile teacherProfile = user.getTeacherProfile();

    if (teacherProfile == null) {
      teacherProfile = TeacherProfile.from(teacherProfileRequest);
    } else {
      teacherProfile.update(teacherProfileRequest);
    }

    user.patchTeacherProfile(teacherProfile);
    user = userRepository.save(user);

    return UserDto.ProfileResponse.from(user);
  }

  @Transactional
  public UserDto.ProfileResponse patchStudentProfile(
    String loginId, StudentProfileRequest studentProfileRequest
  ) {
    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    StudentProfile studentProfile = user.getStudentProfile();

    if (studentProfile == null) {
      studentProfile = StudentProfile.from(studentProfileRequest);
    } else {
      studentProfile.update(studentProfileRequest);
    }

    user.patchStudentProfile(studentProfile);
    userRepository.save(user);

    return UserDto.ProfileResponse.from(user);
  }

  public String uploadProfileImage(String loginId, MultipartFile profileImage) {

    return storageService.store(
      profileImage,
      "users",
      loginId,
      "default-profile"
    );
  }
}
