package promiseofblood.umpabackend.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import promiseofblood.umpabackend.user.dto.UniversityDto;
import promiseofblood.umpabackend.user.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/universities")
@Tag(name = "학교 API")
public class UniversityController {
  private final UserService userService;

  @GetMapping("")
  public ResponseEntity<List<UniversityDto>> universities() {
    return ResponseEntity.ok(userService.listUniversities());
  }
}
