package promiseofblood.umpabackend.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.user.dto.CollegeDto;
import promiseofblood.umpabackend.user.dto.MajorDto;
import promiseofblood.umpabackend.user.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "사용자 API")
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @GetMapping("/register/majors")
  public ResponseEntity<List<MajorDto>> majors() {

    return ResponseEntity.ok(userService.listMajors());
  }

  @GetMapping("/register/colleges")
  public ResponseEntity<List<CollegeDto>> colleges(
      @RequestParam(value = "selected", required = false) List<Long> excludeIds) {

    return ResponseEntity.ok(userService.listColleges(excludeIds));
  }
}
