package kr.co.umpabackend.application.service;

import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import java.io.IOException;
import java.io.InputStream;
import kr.co.umpabackend.domain.vo.FileRole;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

@Service
@RequiredArgsConstructor
public class LightsailStorageService implements StorageService {

  private final String bucket = "umpa-bucket";
  private final S3Template s3Template;
  private final ResourceLoader resourceLoader;

  @Override
  public String upload(MultipartFile file, FileRole role) {

    InputStream inputStream;
    try {
      inputStream = file.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException("파일 업로드를 위한 스트림 생성에 실패했습니다.", e);
    }

    String fullPath = role.createPath(file.getOriginalFilename());

    s3Template.upload(
        bucket,
        fullPath,
        inputStream,
        ObjectMetadata.builder().acl(ObjectCannedACL.PUBLIC_READ).build());

    return fullPath;
  }

  @Override
  public void delete(String filePath) {
    s3Template.deleteObject(bucket, filePath);
  }

  @Override
  public String getFileUrl(String filePath) {
    Resource resource = resourceLoader.getResource("s3://" + bucket + "/" + filePath);

    try {
      return resource.getURL().toString();
    } catch (IOException e) {
      throw new RuntimeException("파일 URL을 가져오는데 실패했습니다.", e);
    }
  }
}
