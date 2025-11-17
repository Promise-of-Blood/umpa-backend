package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.UserService;
import promiseofblood.umpabackend.web.schema.request.DeleteUserRequest;
import promiseofblood.umpabackend.web.schema.request.RegisterByLoginIdPasswordWithRoleRequest;
import promiseofblood.umpabackend.web.schema.response.RetrieveFullProfileResponse;

@RestController
@RequestMapping("api/v1/admin")
@Tag(name = "어드민 API", description = "어드민 기능을 제공하는 API")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;

  @PostMapping(value = "/users/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<RetrieveFullProfileResponse> registerAdmin(
      @Validated @ModelAttribute RegisterByLoginIdPasswordWithRoleRequest request) {

    RetrieveFullProfileResponse created = userService.registerAdmin(request);

    return ResponseEntity.status(201).body(created);
  }

  @GetMapping("/users")
  public ResponseEntity<List<RetrieveFullProfileResponse>> getUser() {
    List<RetrieveFullProfileResponse> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  @DeleteMapping("/users/{loginId}")
  public void deleteUser(
      @PathVariable String loginId, @RequestBody DeleteUserRequest deleteUserRequest) {

    userService.deleteUser(loginId, deleteUserRequest.getIsHardDelete());
  }
}
