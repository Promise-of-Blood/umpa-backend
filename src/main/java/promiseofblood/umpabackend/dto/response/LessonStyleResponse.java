package promiseofblood.umpabackend.dto.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.LessonStyle;

@Getter
@Builder
public class LessonStyleResponse {

  private String code;

  private String name;

  public static LessonStyleResponse of(LessonStyle lessonStyle) {
    return LessonStyleResponse.builder()
      .code(lessonStyle.name())
      .name(lessonStyle.getKoreanName())
      .build();
  }

}
