package kr.co.umpabackend.application.service;

import kr.co.umpabackend.domain.entity.StudentProfile;
import kr.co.umpabackend.domain.entity.TeacherProfile;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.UserRepository;
import kr.co.umpabackend.web.schema.request.PatchDefaultProfileRequest;
import kr.co.umpabackend.web.schema.request.PatchStudentProfileRequest;
import kr.co.umpabackend.web.schema.request.PatchTeacherProfileRequest;
import kr.co.umpabackend.web.schema.response.RetrieveFullProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    return RetrieveFullProfileResponse.from(updatedUser, null);
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

    return RetrieveFullProfileResponse.from(updatedUser, null);
  }

  @Transactional
  public RetrieveFullProfileResponse patchStudentProfile(
      String loginId, PatchStudentProfileRequest studentProfileRequest) {

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

    return RetrieveFullProfileResponse.from(updatedUser, null);
  }

  public String uploadProfileImageIfExists(String loginId, MultipartFile profileImage) {

    if (profileImage == null || profileImage.isEmpty()) {
      return null;
    }

    return storageService.store(profileImage, "users", loginId, "default-profile");
  }
}
