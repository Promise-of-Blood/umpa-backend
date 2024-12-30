package promiseofblood.umpabackend.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import promiseofblood.umpabackend.user.dto.UniversityDto;
import promiseofblood.umpabackend.user.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@BaseUserApiController
@Tag(name = "사용자 관련 API")
public class UserController {

  private final UserService userService;

  @GetMapping("/universities")
  public ResponseEntity<List<UniversityDto>> universities() {
    return ResponseEntity.ok(userService.listUniversities());
  }
}
