package kr.co.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import kr.co.umpabackend.application.service.StorageService;
import kr.co.umpabackend.domain.vo.FileRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/storage")
@RequiredArgsConstructor
@Tag(name = "정적 파일 저장소 API")
public class S3Controller {

  private final StorageService storageService;

  @GetMapping
  public String getFileUrl(@RequestParam String fileName) {
    return storageService.getFileUrl(fileName);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public void uploadFile(MultipartFile multipartFile) {
    var result = storageService.upload(multipartFile, FileRole.USER_PROFILE);
    System.out.println(result);
  }

  @DeleteMapping
  public void deleteFile(@RequestParam String fileName) {
    storageService.delete(fileName);
  }
}
