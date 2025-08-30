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
import promiseofblood.umpabackend.web.schema.request.PatchTeacherProfileRequest;
import promiseofblood.umpabackend.web.schema.request.PatchDefaultProfileRequest;
import promiseofblood.umpabackend.web.schema.response.RetrieveFullProfileResponse;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final UserRepository userRepository;
  private final StorageService storageService;

  @Transactional
  public RetrieveFullProfileResponse patchDefaultProfile(
    String loginId, PatchDefaultProfileRequest patchDefaultProfileRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    user.patchDefaultProfile(
      patchDefaultProfileRequest.getUsername(),
      patchDefaultProfileRequest.getGender(),
      uploadProfileImageIfExists(user.getLoginId(), patchDefaultProfileRequest.getProfileImage()),
      patchDefaultProfileRequest.getProfileType());
    User updatedUser = userRepository.save(user);

    return RetrieveFullProfileResponse.from(updatedUser);
  }

  @Transactional
  public RetrieveFullProfileResponse patchTeacherProfile(
    String loginId, PatchTeacherProfileRequest teacherProfileRequest) {

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

    return RetrieveFullProfileResponse.from(updatedUser);
  }

  @Transactional
  public RetrieveFullProfileResponse patchStudentProfile(
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

    return RetrieveFullProfileResponse.from(updatedUser);
  }

  public String uploadProfileImageIfExists(String loginId, MultipartFile profileImage) {

    if (profileImage == null || profileImage.isEmpty()) {
      return null;
    }

    return storageService.store(profileImage, "users", loginId, "default-profile");
  }
}
