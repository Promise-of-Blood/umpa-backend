package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.LessonServicePost;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder
public class RetrieveLessonServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ServiceCostResponse serviceCost;

  private ConstantResponse<Subject> subject; // 상수

  private List<ConstantResponse<WeekDay>> availableWeekDays; // 상수

  private ConstantResponse<LessonStyle> lessonStyle; // 상수

  private Boolean isDemoLessonProvided;

  private Integer demoLessonCost;

  private List<String> recommendedTargets;

  private List<ListCurriculumResponse> curriculums;

  private List<String> studioPhotoUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static RetrieveLessonServicePostResponse of(LessonServicePost lessonServicePost) {

    List<ListCurriculumResponse> curriculumResponses =
        lessonServicePost.getCurriculums().stream()
            .map(c -> ListCurriculumResponse.of(c.getTitle(), c.getContent()))
            .toList();

    return RetrieveLessonServicePostResponse.builder()
        .id(lessonServicePost.getId())
        .thumbnailImage(lessonServicePost.getThumbnailImageUrl())
        .title(lessonServicePost.getTitle())
        .description(lessonServicePost.getDescription())
        .serviceCost(ServiceCostResponse.from(lessonServicePost.getServiceCost()))
        .subject(new ConstantResponse<>(lessonServicePost.getSubject()))
        .availableWeekDays(
            lessonServicePost.getAvailableWeekDays().stream().map(ConstantResponse::new).toList())
        .lessonStyle(new ConstantResponse<>(lessonServicePost.getLessonStyle()))
        .isDemoLessonProvided(lessonServicePost.getIsDemoLessonProvided())
        .demoLessonCost(lessonServicePost.getDemoLessonCost())
        .recommendedTargets(lessonServicePost.getRecommendedTargets())
        .curriculums(curriculumResponses)
        .studioPhotoUrls(lessonServicePost.getStudioPhotoUrls())
        .teacherProfile(TeacherAuthorProfileDto.from(lessonServicePost.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
