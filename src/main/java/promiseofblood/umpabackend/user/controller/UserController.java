package promiseofblood.umpabackend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

  @Operation(summary = "전공 목록 조회", description = "회원가입 시, UI에서 사용자가 선택할 수 있는 전공 목록을 조회합니다.")
  @GetMapping("/register/majors")
  public ResponseEntity<List<MajorDto>> majors() {

    return ResponseEntity.ok(userService.listMajors());
  }

  @Operation(summary = "대학교 목록 조회", description = "회원가입 시, UI에서 사용자가 선택할 수 있는 지망 대학교 목록을 조회합니다.")
  @GetMapping("/register/colleges")
  public ResponseEntity<List<CollegeDto>> colleges() {
    return ResponseEntity.ok(userService.listColleges());
  }
}
