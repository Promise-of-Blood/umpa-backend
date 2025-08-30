package promiseofblood.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Major;

@Getter
@Builder
@AllArgsConstructor
public class PatchStudentProfileRequest {

  @Schema(description = "전공", example = "PIANO")
  private final Major major;

  @Schema(
      description = "선호하는 학교",
      example = "[\"GANGDONG_UNIVERSITY\", \"KYONGBUK_SCIENCE_COLLEGE\"]")
  private final List<College> preferredColleges;

  @Schema(description = "학년", example = "HIGH_3")
  private final Grade grade;
}
