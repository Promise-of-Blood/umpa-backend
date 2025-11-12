package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

@Entity
@Getter
@Table(name = "lesson_service_posts")
@DiscriminatorValue("LESSON")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonServicePost extends ServicePost {

  // 제목, 설명, 썸네일 이미지, 유저, 리뷰(상속)

  @Embedded private ServiceCost serviceCost;

  @Enumerated(EnumType.STRING)
  private Subject subject;

  // 가능한 수업 지역들은 검색가능해야하므로, ElementCollection으로 설정
  @Enumerated(EnumType.STRING)
  @ElementCollection
  private List<Region> availableRegions;

  // 가능한 수업 요일들
  @Enumerated(EnumType.STRING)
  @ElementCollection
  private List<WeekDay> availableWeekDays;

  // 수업 스타일(비대면/대면)
  @Enumerated(EnumType.STRING)
  private LessonStyle lessonStyle;

  // 데모 레슨 제공여부 및 비용
  private Boolean isDemoLessonProvided;

  private Integer demoLessonCost;

  // 추천 대상
  @ElementCollection private List<String> recommendedTargets;

  // 커리큘럼
  @ElementCollection
  @CollectionTable(
      name = "lesson_service_post_curriculums",
      joinColumns = @JoinColumn(name = "lesson_service_post_id"))
  private List<LessonCurriculum> curriculums = new ArrayList<>();

  // 스튜디오 사진들
  @ElementCollection private List<String> studioPhotoUrls;

  public static LessonServicePost create(
      // 공통 필드
      User user,
      String thumbnailImageUrl,
      String title,
      String description,
      // 레슨 전용 필드
      ServiceCost serviceCost,
      Subject subject,
      List<Region> availableRegions,
      List<WeekDay> availableWeekDays,
      LessonStyle lessonStyle,
      boolean isDemoLessonProvided,
      Integer demoLessonCost,
      List<String> recommendedTargets,
      List<LessonCurriculum> curriculums,
      List<String> studioPhotoUrls) {

    var post =
        LessonServicePost.builder()
            // 공통 필드
            .user(user)
            .thumbnailImageUrl(thumbnailImageUrl)
            .title(title)
            .description(description)
            // 레슨 전용 필드
            .serviceCost(serviceCost)
            .subject(subject)
            .availableRegions(availableRegions)
            .availableWeekDays(availableWeekDays)
            .lessonStyle(lessonStyle)
            .isDemoLessonProvided(isDemoLessonProvided)
            .demoLessonCost(demoLessonCost)
            .recommendedTargets(recommendedTargets)
            .curriculums(curriculums)
            .studioPhotoUrls(studioPhotoUrls)
            .build();

    return post;
  }
}
