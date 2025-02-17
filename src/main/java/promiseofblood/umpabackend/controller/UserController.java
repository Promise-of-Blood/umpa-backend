package promiseofblood.umpabackend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import promiseofblood.umpabackend.dto.response.UserResponse;
import promiseofblood.umpabackend.service.UserService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Tag(name = "사용자 관리 API")
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  @Operation(summary = "사용자 목록 조회", description = "응답 형식 확정되지 않았음다 ㅠ")
  @GetMapping("")
  public ResponseEntity<List<UserResponse>> getUsers() {
    return ResponseEntity.ok(userService.getUsers());
  }

  @Operation(summary = "id로 특정되는 사용자 삭제")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }

}
