package kr.co.umpabackend.web.schema.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListCurriculumResponse {

  private String title;

  private String content;

  public static ListCurriculumResponse of(String title, String content) {
    return new ListCurriculumResponse(title, content);
  }
}
