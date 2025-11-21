package kr.co.umpabackend.web.schema.response;

import java.util.List;
import kr.co.umpabackend.domain.entity.LessonServicePost;
import kr.co.umpabackend.domain.vo.LessonStyle;
import kr.co.umpabackend.domain.vo.Region;
import kr.co.umpabackend.domain.vo.Subject;
import kr.co.umpabackend.domain.vo.WeekDay;
import lombok.Builder;
import lombok.Getter;

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

  private List<ConstantResponse<Region>> availableRegions;

  private Boolean isDemoLessonProvided;

  private Integer demoLessonCost;

  private List<String> recommendedTargets;

  private List<ListCurriculumResponse> curriculums;

  private List<String> studioPhotoUrls;

  private RetrieveTeacherAuthorProfileResponse teacherProfile;

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
        .availableRegions(
            lessonServicePost.getAvailableRegions().stream().map(ConstantResponse::new).toList())
        .isDemoLessonProvided(lessonServicePost.getIsDemoLessonProvided())
        .demoLessonCost(lessonServicePost.getDemoLessonCost())
        .recommendedTargets(lessonServicePost.getRecommendedTargets())
        .curriculums(curriculumResponses)
        .studioPhotoUrls(lessonServicePost.getStudioPhotoUrls())
        .teacherProfile(RetrieveTeacherAuthorProfileResponse.from(lessonServicePost.getUser()))
        .reviewRating(0.0f)
        .build();
  }
}
