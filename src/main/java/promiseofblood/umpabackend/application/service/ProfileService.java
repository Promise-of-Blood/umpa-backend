package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.entity.StudentProfile;
import promiseofblood.umpabackend.domain.entity.TeacherProfile;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.dto.StudentProfileDto;
import promiseofblood.umpabackend.dto.TeacherProfileDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.UserDto.DefaultProfilePatchRequest;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;
  private final StorageService storageService;

  @Transactional
  public UserDto.ProfileResponse patchDefaultProfile(
    String loginId, DefaultProfilePatchRequest defaultProfilePatchRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    user.patchDefaultProfile(
      defaultProfilePatchRequest.getUsername(),
      defaultProfilePatchRequest.getGender(),
      uploadProfileImageIfExists(user.getLoginId(), defaultProfilePatchRequest.getProfileImage()),
      defaultProfilePatchRequest.getProfileType());
    User updatedUser = userRepository.save(user);

    return UserDto.ProfileResponse.from(updatedUser);
  }

  @Transactional
  public UserDto.ProfileResponse patchTeacherProfile(
    String loginId, TeacherProfileDto.TeacherProfileRequest teacherProfileRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    TeacherProfile teacherProfile = user.getTeacherProfile();

    if (teacherProfile == null) {
      teacherProfile = TeacherProfile.empty();
    }

    teacherProfile.update(teacherProfileRequest);

    user.patchTeacherProfile(teacherProfile);
    User updatedUser = userRepository.save(user);

    return UserDto.ProfileResponse.from(updatedUser);
  }

  @Transactional
  public UserDto.ProfileResponse patchStudentProfile(
    String loginId, StudentProfileDto.StudentProfileRequest studentProfileRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    StudentProfile studentProfile = user.getStudentProfile();

    if (studentProfile == null) {
      studentProfile = StudentProfile.empty();
    }

    studentProfile.update(studentProfileRequest);

    user.patchStudentProfile(studentProfile);
    User updatedUser = userRepository.save(user);

    return UserDto.ProfileResponse.from(updatedUser);
  }

  public String uploadProfileImageIfExists(String loginId, MultipartFile profileImage) {

    if (profileImage == null || profileImage.isEmpty()) {
      return null;
    }

    return storageService.store(profileImage, "users", loginId, "default-profile");
  }
}
