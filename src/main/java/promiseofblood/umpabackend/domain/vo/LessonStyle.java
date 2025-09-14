package promiseofblood.umpabackend.domain.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LessonStyle {
  FACE_TO_FACE("대면"),

  NON_FACE_TO_FACE("비대면"),

  ALL("전체");

  private final String koreanName;
}
