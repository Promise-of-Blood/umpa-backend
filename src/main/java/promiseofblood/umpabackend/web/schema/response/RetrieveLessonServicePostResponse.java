package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.LessonServicePost;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder
public class RetrieveLessonServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private CostPerUnitDto costPerUnit;

  //
  private Subject subject;

  private List<WeekDay> availableWeekDays;

  private String lessonStyle;

  private Boolean isDemoLessonProvided;

  private Integer demoLessonCost;

  private List<String> recommendedTargets;

  private List<ListCurriculumResponse> curriculums;

  private List<String> studioPhotoUrls;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;

  public static RetrieveLessonServicePostResponse of(LessonServicePost post) {

    List<ListCurriculumResponse> curriculumResponses =
        post.getCurriculums().stream()
            .map(c -> ListCurriculumResponse.of(c.getTitle(), c.getContent()))
            .toList();

    return RetrieveLessonServicePostResponse.builder()
        .id(post.getId())
        .thumbnailImage(post.getThumbnailImageUrl())
        .title(post.getTitle())
        .description(post.getDescription())
        .costPerUnit(CostPerUnitDto.from(post.getServiceCost()))
        .subject(post.getSubject())
        .availableWeekDays(post.getAvailableWeekDays())
        .lessonStyle(post.getLessonStyle().name())
        .isDemoLessonProvided(post.getIsDemoLessonProvided())
        .demoLessonCost(post.getDemoLessonCost())
        .recommendedTargets(post.getRecommendedTargets())
        .curriculums(curriculumResponses)
        .studioPhotoUrls(post.getStudioPhotoUrls())
        .teacherProfile(TeacherAuthorProfileDto.from(post.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
