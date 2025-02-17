package promiseofblood.umpabackend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.dto.CollegeDto;
import promiseofblood.umpabackend.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "학교 API")
@RestController
@RequestMapping("/api/colleges")
public class CollegeController {

  private final UserService userService;

  @Operation(summary = "대학교 목록 조회")
  @GetMapping("")
  public ResponseEntity<List<CollegeDto>> colleges() {

    return ResponseEntity.ok(userService.listColleges());
  }
}
