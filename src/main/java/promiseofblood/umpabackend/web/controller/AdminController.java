package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.UserService;
import promiseofblood.umpabackend.web.schema.request.RegisterByLoginIdPasswordWithRoleRequest;
import promiseofblood.umpabackend.web.schema.response.RetrieveFullProfileResponse;

@RestController
@RequestMapping("api/v1/admin")
@Tag(name = "어드민 API", description = "어드민 기능을 제공하는 API")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;

  @Tag(name = "회원가입 API")
  @PostMapping(value = "/users/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<RetrieveFullProfileResponse> registerAdmin(
      @Validated @ModelAttribute RegisterByLoginIdPasswordWithRoleRequest request) {

    RetrieveFullProfileResponse created = userService.registerAdmin(request);

    return ResponseEntity.status(201).body(created);
  }
}
