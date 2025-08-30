package promiseofblood.umpabackend.web.schema.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import promiseofblood.umpabackend.domain.vo.Major;

@Builder
@Getter
@AllArgsConstructor
public class PatchTeacherProfileRequest {

  @Schema(description = "대표 문구", example = "멋쟁이 토마토")
  private String keyphrase;

  @Schema(
    description = "선생님 소개",
    example = "안녕하세요, 저는 전자 음악을 전공한 선생님입니다. 레슨을 통해 여러분과 함께 음악의 즐거움을 나누고 싶습니다.")
  private String description;

  @Schema(description = "선생님 전공", example = "ELECTRONIC_MUSIC")
  private Major major;

  @Schema(
    description = "경력",
    example =
      "["
        + "{\"title\":\"인덕대학교 컴퓨터소프트웨어학과 졸업\",\"start\":\"2020.03\",\"end\":\"2024.02\",\"isRepresentative\":true},"
        + "{\"title\":\"서울대학교 컴퓨터소프트웨어학과 졸업\",\"start\":\"2020.03\",\"end\":\"2024.02\",\"isRepresentative\":true}"
        + "]")
  private List<TeacherCareerRequest> careers;

  @Schema(example = "[\"https://gdsnadevlog.com\",\"https://www.gdsnadevlog.com\"]")
  private List<String> links;

  @Builder
  @Getter
  @AllArgsConstructor
  public static class TeacherCareerRequest {

    private String title;

    @DateTimeFormat(pattern = "yyyy.MM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM", timezone = "Asia/Seoul")
    private YearMonth start;

    @DateTimeFormat(pattern = "yyyy.MM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM", timezone = "Asia/Seoul")
    private YearMonth end;

    private boolean isRepresentative;
  }
}
