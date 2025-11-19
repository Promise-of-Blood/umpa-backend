package promiseofblood.umpabackend.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.command.CreateLessonServicePostCommand;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.domain.entity.LessonCurriculum;
import promiseofblood.umpabackend.domain.entity.LessonServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.LessonServicePostRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.web.schema.response.ListLessonServicePostResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveLessonServicePostResponse;

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
      CreateLessonServicePostCommand command) {

    User user =
        userRepository
            .findByLoginId(command.getLoginId())
            .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

    List<String> studioPhotoUrls;
    if (command.getStudioPhotos() == null) {
      studioPhotoUrls = List.of();
    } else {
      studioPhotoUrls =
          command.getStudioPhotos().stream()
              .map(
                  file ->
                      storageService.store(
                          file, "service/" + user.getId() + "/lesson/studio-photos"))
              .toList();
    }

    List<LessonCurriculum> curriculums =
        command.getCurriculums().stream()
            .map(c -> new LessonCurriculum(c.getTitle(), c.getContent()))
            .toList();

    LessonServicePost lessonServicePost =
        LessonServicePost.create(
            user,
            storageService.store(
                command.getThumbnailImage(), "service/" + user.getId() + "/lesson"),
            command.getTitle(),
            command.getDescription(),
            ServiceCost.of(command.getServiceCostValue(), command.getServiceCostUnit()),
            command.getSubject(),
            command.getAvailableRegions(),
            command.getAvailableWeekDays(),
            command.getLessonStyle(),
            command.isDemoLessonProvided(),
            command.getDemoLessonCost(),
            command.getRecommendedTargets(),
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
