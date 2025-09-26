package promiseofblood.umpabackend.web.schema.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.LessonServicePost;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

@Getter
@Builder
public class ListLessonServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ServiceCostResponse serviceCost;

  private ConstantResponse<Subject> subject;

  private List<ConstantResponse<WeekDay>> availableWeekDays;

  private ConstantResponse<LessonStyle> lessonStyle;

  private List<ConstantResponse<Region>> availableRegions;

  private String teacherName;

  private float reviewRating;

  public static ListLessonServicePostResponse of(LessonServicePost lessonServicePost) {

    return ListLessonServicePostResponse.builder()
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
        .teacherName(String.valueOf((lessonServicePost.getUser().getUsername())))
        .reviewRating(0.0f)
        .build();
  }
}
