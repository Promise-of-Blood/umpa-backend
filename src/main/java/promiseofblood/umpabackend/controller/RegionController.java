package promiseofblood.umpabackend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.dto.RegionalLocalGovernmentDto;
import promiseofblood.umpabackend.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "지역 API")
@RestController
@RequestMapping("/api/regions")
public class RegionController {
  private final UserService userService;

  @Operation(summary = "지역 목록 조회")
  @GetMapping("")
  public ResponseEntity<List<RegionalLocalGovernmentDto>> regions() {

    return ResponseEntity.ok(userService.listRegions());
  }
}
