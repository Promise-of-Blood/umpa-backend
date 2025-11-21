package kr.co.umpabackend.web.schema.response;

import java.util.List;
import kr.co.umpabackend.domain.entity.LessonServicePost;
import kr.co.umpabackend.domain.vo.LessonStyle;
import kr.co.umpabackend.domain.vo.PostDisplayStatus;
import kr.co.umpabackend.domain.vo.Region;
import kr.co.umpabackend.domain.vo.Subject;
import kr.co.umpabackend.domain.vo.WeekDay;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ListLessonServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private ConstantResponse<PostDisplayStatus> displayStatus;

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
        .displayStatus(new ConstantResponse<>(lessonServicePost.getDisplayStatus()))
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
