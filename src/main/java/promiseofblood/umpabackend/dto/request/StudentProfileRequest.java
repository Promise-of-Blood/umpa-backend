package promiseofblood.umpabackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
public class StudentProfileRequest {

  @Schema(description = "선호하는 학교", example = "[\"GANGDONG_UNIVERSITY\", \"KYONGBUK_SCIENCE_COLLEGE\"]")
  private List<String> preferredColleges;

  @NotBlank(message = "학년은 필수입니다.")
  @Schema(description = "학년", example = "HIGH_3")
  private String grade;

  @NotNull(message = "희망 과목은 필수입니다.")
  @Schema(description = "희망 과목", example = "PIANO")
  private String subject;

  @NotBlank(message = "수업 방식은 필수입니다.")
  @Schema(description = "수업 방식", example = "[\"FACE_TO_FACE\", \"NON_FACE_TO_FACE\"]")
  private List<String> lessonStyles;

  @NotNull(message = "레슨 가능한 요일은 필수입니다.")
  @Schema(description = "레슨 가능한 요일", example = "[\"MONDAY\", \"WEDNESDAY\", \"FRIDAY\"]")
  private List<String> availableWeekdays;

  @Size(max = 1000, message = "수업 요청서는 1000자 이하로 작성해주세요.")
  @Schema(description = "수업 요청서", example = "비대면 위주로, 성실하게 알려주실 분을 구합니다.")
  private String lessonRequirements;

}
