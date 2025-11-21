package kr.co.umpabackend.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import kr.co.umpabackend.domain.vo.LessonStyle;
import kr.co.umpabackend.domain.vo.Region;
import kr.co.umpabackend.domain.vo.Subject;
import kr.co.umpabackend.domain.vo.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

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

  @NotNull final List<CreateLessonCurriculumCommand> curriculums;
  @NotNull final List<String> recommendedTargets;
  @NotNull final List<MultipartFile> studioPhotos;

  @Getter
  @AllArgsConstructor
  public static class CreateLessonCurriculumCommand {

    @NotNull final String title;
    @NotNull final String content;

    public static CreateLessonCurriculumCommand of(String oneLineValue) {
      String[] parts = oneLineValue.split(":", 2);
      return new CreateLessonCurriculumCommand(parts[0], parts[1]);
    }
  }
}
