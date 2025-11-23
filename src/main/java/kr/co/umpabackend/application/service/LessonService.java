package kr.co.umpabackend.application.service;

import java.util.ArrayList;
import java.util.List;
import kr.co.umpabackend.application.exception.ResourceNotFoundException;
import kr.co.umpabackend.domain.entity.LessonCurriculum;
import kr.co.umpabackend.domain.entity.LessonServicePost;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.LessonServicePostRepository;
import kr.co.umpabackend.domain.repository.UserRepository;
import kr.co.umpabackend.domain.vo.FileRole;
import kr.co.umpabackend.domain.vo.ServiceCost;
import kr.co.umpabackend.web.schema.request.CreateLessonServicePostRequest;
import kr.co.umpabackend.web.schema.response.ListLessonServicePostResponse;
import kr.co.umpabackend.web.schema.response.RetrieveLessonServicePostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LessonService {

  private final StorageService storageService;
  private final UserRepository userRepository;
  private final LessonServicePostRepository lessonServicePostRepository;

  @Transactional(readOnly = true)
  public Page<ListLessonServicePostResponse> getAllServices(int page, int size) {

    Page<LessonServicePost> servicePostPage =
        lessonServicePostRepository.findAll(PageRequest.of(page, size));

    return servicePostPage.map(ListLessonServicePostResponse::of);
  }

  @Transactional
  public RetrieveLessonServicePostResponse createLessonServicePost(
      CreateLessonServicePostRequest payload, String loginId) {

    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

    String thumbnailImagePath =
        storageService.upload(payload.getThumbnailImage(), FileRole.LESSON_THUMBNAIL);
    String thumbnailImageUrl = storageService.getFileUrl(thumbnailImagePath);

    List<String> studioPhotoUrls = new ArrayList<>();
    if (payload.getStudioPhotos() == null) {
      studioPhotoUrls = List.of();
    } else {
      for (MultipartFile file : payload.getStudioPhotos()) {
        String path = storageService.upload(file, FileRole.LESSON_STUDIO);
        String url = storageService.getFileUrl(path);
        studioPhotoUrls.add(url);
      }
    }

    List<LessonCurriculum> curriculums = new ArrayList<>();
    for (String curriculumEntry : payload.getCurriculums()) {
      String[] parts = curriculumEntry.split(":", 2);
      if (parts.length != 2) {
        throw new IllegalArgumentException(
            "커리큘럼 항목은 '제목: 내용' 형식이어야 합니다. 잘못된 항목: " + curriculumEntry);
      }
      String title = parts[0].trim();
      String content = parts[1].trim();
      LessonCurriculum curriculum = new LessonCurriculum(title, content);
      curriculums.add(curriculum);
    }

    LessonServicePost lessonServicePost =
        LessonServicePost.create(
            user,
            thumbnailImageUrl,
            payload.getTitle(),
            payload.getDescription(),
            ServiceCost.of(payload.getCost(), "시간"),
            payload.getSubject(),
            payload.getAvailableRegions(),
            payload.getAvailableWeekDays(),
            payload.getLessonStyle(),
            payload.getIsDemoLessonProvided(),
            payload.getDemoLessonCost(),
            payload.getRecommendedTargets(),
            curriculums,
            studioPhotoUrls);

    lessonServicePostRepository.save(lessonServicePost);

    return RetrieveLessonServicePostResponse.of(lessonServicePost);
  }

  @Transactional(readOnly = true)
  public RetrieveLessonServicePostResponse retrieveLessonServicePost(Long postId) {
    LessonServicePost lessonServicePost =
        lessonServicePostRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("레슨 서비스 게시글을 찾을 수 없습니다."));

    return RetrieveLessonServicePostResponse.of(lessonServicePost);
  }
}
