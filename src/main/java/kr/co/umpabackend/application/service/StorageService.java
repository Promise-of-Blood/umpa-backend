package kr.co.umpabackend.application.service;

import kr.co.umpabackend.domain.vo.FileRole;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

  /**
   * 파일을 저장소에 업로드합니다.
   *
   * @param multipartFile 파일의 바이트 스트림
   * @param role 파일의 역할 (예: PROFILE_IMAGE, DOCUMENT 등)
   * @return 업로드된 파일의 경로 (String)
   */
  String upload(MultipartFile multipartFile, FileRole role);

  /**
   * 파일을 삭제합니다.
   *
   * @param filePath 삭제할 파일 경로
   */
  void delete(String filePath);

  /**
   * 파일에 접근할 수 있는 URL을 반환합니다.
   *
   * @param filePath 파일 경로
   * @return 접근 가능한 URL (String)
   */
  String getFileUrl(String filePath);
}
