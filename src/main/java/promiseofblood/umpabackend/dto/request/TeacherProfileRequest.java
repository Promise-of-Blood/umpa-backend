package promiseofblood.umpabackend.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.YearMonth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

@Builder
@Getter
@AllArgsConstructor
public class TeacherProfileRequest {

  @Schema(description = "레슨 지역", example = "SEOUL_GWANGJINGU")
  private String lessonRegion;

  @Schema(
    description = "경력",
    example = "["
      + "{\"title\":\"인덕대학교 컴퓨터소프트웨어학과 졸업\",\"startDate\":\"2020.03\",\"endDate\":\"2024.02\",\"isRepresentative\":true},"
      + "{\"title\":\"서울대학교 컴퓨터소프트웨어학과 졸업\",\"startDate\":\"2020.03\",\"endDate\":\"2024.02\",\"isRepresentative\":true}"
      + "]"
  )
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
    private YearMonth startDate;

    @DateTimeFormat(pattern = "yyyy.MM")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM", timezone = "Asia/Seoul")
    private YearMonth endDate;

    private boolean isRepresentative;

  }
}

