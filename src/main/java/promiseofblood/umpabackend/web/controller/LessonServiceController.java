package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class LessonServiceController {

  // *************
  // * 레슨 서비스 *
  // *************
  @Tag(name = "서비스 관리 API(레슨)")
  @PostMapping("/lesson")
  @PreAuthorize("isAuthenticated()")
  public void registerLesson() {}
}
