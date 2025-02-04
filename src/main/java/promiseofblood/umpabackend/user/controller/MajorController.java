package promiseofblood.umpabackend.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.user.dto.MajorDto;
import promiseofblood.umpabackend.user.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "전공 API")
@RestController
@RequestMapping("/api/majors")
public class MajorController {

  private final UserService userService;

  @Operation(summary = "전공 목록 조회")
  @GetMapping("")
  public ResponseEntity<List<MajorDto>> majors() {

    return ResponseEntity.ok(userService.listMajors());
  }
}
