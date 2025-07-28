package promiseofblood.umpabackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Major;

@Builder
@Getter
@AllArgsConstructor
public class StudentProfileRequest {

  @Schema(description = "전공", example = "PIANO")
  private Major major;

  @Schema(description = "선호하는 학교", example = "[\"GANGDONG_UNIVERSITY\", \"KYONGBUK_SCIENCE_COLLEGE\"]")
  private List<College> preferredColleges;

  @Schema(description = "학년", example = "HIGH_3")
  private Grade grade;
}
