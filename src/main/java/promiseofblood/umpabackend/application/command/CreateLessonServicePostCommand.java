package promiseofblood.umpabackend.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Region;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

@Getter
@Builder
public class CreateLessonServicePostCommand {

  @NotNull final String loginId;

  @NotNull final MultipartFile thumbnailImage;
  @NotNull final String title;
  @NotNull final String description;

  @NotNull final Integer serviceCostValue;
  @NotNull final String serviceCostUnit;

  @NotNull final Subject subject;
  @NotNull final List<Region> availableRegions;
  @NotNull final List<WeekDay> availableWeekDays;
  @NotNull final LessonStyle lessonStyle;

  @NotNull final boolean isDemoLessonProvided;
  @NotNull final Integer demoLessonCost;

  @NotNull final List<String> recommendedTargets;
  @NotNull final List<MultipartFile> studioPhotos;
}
