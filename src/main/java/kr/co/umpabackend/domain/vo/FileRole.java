package kr.co.umpabackend.domain.vo;

import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FileRole {

  // === 1. USER 관련 ===
  USER_PROFILE("media/users/profiles"),

  // === 2. LESSON (ServicePost) 관련 ===
  LESSON_THUMBNAIL("media/lessons/thumbnails"),
  LESSON_STUDIO("media/lessons/studios"),

  // === 3. STATIC (관리자/개발용) ===
  STATIC_DEFAULT_IMAGE("static/defaults");

  private final String basePath;

  /** 실제 S3에 저장될 전체 경로(Key)를 생성합니다. 정책: BasePath / + UUID + Extension */
  public String createPath(String originalFilename) {
    String ext = extractExtension(originalFilename);
    String uuid = UUID.randomUUID().toString();

    return String.format("%s/%s%s", this.basePath, uuid, ext);
  }

  private String extractExtension(String originalFilename) {
    try {
      return originalFilename.substring(originalFilename.lastIndexOf("."));
    } catch (Exception e) {
      return "";
    }
  }
}
