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

  private List<String> preferredColleges;

  @NotBlank(message = "학년은 필수입니다.")
  private String grade;

  @NotNull(message = "희망 과목은 필수입니다.")
  private String preferredSubject;

  @NotBlank(message = "수업 방식은 필수입니다.")
  private String lessonStyle;

  @NotNull(message = "레슨 가능한 요일은 필수입니다.")
  private String availableWeekdays;

  @Schema(description = "수업 요청서", example = "수업 요청서 내용")
  @Size(max = 1000, message = "수업 요청서는 1000자 이하로 작성해주세요.")
  private String lessonRequirements;

}
