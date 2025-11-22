package kr.co.umpabackend.web.schema.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import kr.co.umpabackend.domain.vo.College;
import kr.co.umpabackend.domain.vo.Grade;
import kr.co.umpabackend.domain.vo.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
