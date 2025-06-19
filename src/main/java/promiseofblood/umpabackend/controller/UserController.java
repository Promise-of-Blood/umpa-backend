package promiseofblood.umpabackend.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.domain.service.UserService;
import promiseofblood.umpabackend.dto.UserDto;

@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/users")
  public ResponseEntity<List<UserDto>> getUser() {
    List<UserDto> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/users")
  public ResponseEntity<Void> deleteUser() {
    userService.deleteUsers();

    return ResponseEntity.noContent().build();
  }

}
