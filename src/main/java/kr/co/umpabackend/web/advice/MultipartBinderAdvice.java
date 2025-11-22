package kr.co.umpabackend.web.advice;

import java.beans.PropertyEditorSupport;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartFile;

@RestControllerAdvice
public class MultipartBinderAdvice {
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.registerCustomEditor(
        MultipartFile.class,
        new PropertyEditorSupport() {
          @Override
          public void setAsText(String text) {
            if (text == null || text.isBlank()) {
              setValue(null);
            } else {
              throw new IllegalArgumentException("Invalid MultipartFile value");
            }
          }
        });
  }
}
