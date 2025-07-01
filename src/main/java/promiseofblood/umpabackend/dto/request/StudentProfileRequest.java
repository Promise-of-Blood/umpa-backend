package promiseofblood.umpabackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;

@Builder
@Getter
@AllArgsConstructor
public class StudentProfileRequest {

  @Schema(description = "전공", example = "PIANO")
  private Major major;

  @Schema(description = "선호하는 학교", example = "[\"GANGDONG_UNIVERSITY\", \"KYONGBUK_SCIENCE_COLLEGE\"]")
  private List<College> preferredColleges;

  //  @NotBlank(message = "학년은 필수입니다.")
  @Schema(description = "학년", example = "HIGH_3")
  private Grade grade;

  //  @NotNull(message = "희망 과목은 필수입니다.")
  @Schema(description = "희망 과목", example = "PIANO")
  private Subject subject;

  //  @NotBlank(message = "수업 방식은 필수입니다.")
  @Schema(description = "수업 방식", example = "[\"FACE_TO_FACE\", \"NON_FACE_TO_FACE\"]")
  private List<LessonStyle> lessonStyles;

  //  @NotNull(message = "레슨 가능한 요일은 필수입니다.")
  @Schema(description = "레슨 가능한 요일", example = "[\"MONDAY\", \"WEDNESDAY\", \"FRIDAY\"]")
  private List<WeekDay> availableWeekdays;

  @Size(max = 1000, message = "수업 요청서는 1000자 이하로 작성해주세요.")
  @Schema(description = "수업 요청서", example = "비대면 위주로, 성실하게 알려주실 분을 구합니다.")
  private String lessonRequirements;

}
