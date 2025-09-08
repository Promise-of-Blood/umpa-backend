package promiseofblood.umpabackend.infrastructure.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

public class ImageFileValidator implements ConstraintValidator<ValidImageFile, MultipartFile> {

  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if (file == null || file.isEmpty()) return false;

    // MIME 타입 체크
    String type = file.getContentType();
    if (!"image/jpeg".equalsIgnoreCase(type) && !"image/jpg".equalsIgnoreCase(type)) {
      return false;
    }

    // 이미지 헤더 체크
    try (InputStream input = file.getInputStream()) {
      BufferedImage image = ImageIO.read(input);
      if (image == null) return false;
    } catch (IOException e) {
      return false;
    }

    return true;
  }
}
