package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.DayOfWeek;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.ServiceCost;

@Entity
@Table(name = "lesson_service_posts")
@DiscriminatorValue("LESSON")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LessonServicePost extends ServicePost {

  @Embedded
  private ServiceCost serviceCost;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<Instrument> lessonSubjects;

  @ElementCollection
  @Enumerated(EnumType.STRING)
  private List<DayOfWeek> availableWeekDays;

  @Enumerated(EnumType.STRING)
  private LessonStyle lessonStyle;

  private boolean isDemoLessonOptionAvailable;

  @ElementCollection
  private List<String> recommendedTargets;

  @ElementCollection
  private List<String> studioPhotoUrls;

  //  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  //  private List<LessonCurriculum> curriculum;

  @Override
  public String getCostAndUnit() {
    return serviceCost.getCost() + " " + serviceCost.getUnit();
  }
}
