package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;

@Getter
public enum LessonStyle {
  FACE_TO_FACE("대면"),
  NON_FACE_TO_FACE("비대면");

  private final String name;

  LessonStyle(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
