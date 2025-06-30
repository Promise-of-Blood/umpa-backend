package promiseofblood.umpabackend.controller;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.core.security.SecurityUserDetails;
import promiseofblood.umpabackend.domain.service.Oauth2Service;
import promiseofblood.umpabackend.domain.service.StorageService;
import promiseofblood.umpabackend.domain.service.UserService;
import promiseofblood.umpabackend.dto.JwtPairDto;
import promiseofblood.umpabackend.dto.Oauth2ProviderDto;
import promiseofblood.umpabackend.dto.TeacherProfileDto;
import promiseofblood.umpabackend.dto.UserDto;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.DefaultProfileRequest;
import promiseofblood.umpabackend.dto.request.GeneralLoginRequest;
import promiseofblood.umpabackend.dto.request.GeneralRegisterRequest;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;
import promiseofblood.umpabackend.dto.request.TeacherProfileRequest;
import promiseofblood.umpabackend.dto.request.TokenRefreshRequest;
import promiseofblood.umpabackend.dto.response.RegisterCompleteResponse;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "사용자 관리 API", description = "사용자 등록, 조회, 삭제 및 OAuth2 인증 관련 API")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final Oauth2Service oauth2Service;
  private final StorageService storageService;

  @PostMapping("/register/general")
  public ResponseEntity<RegisterCompleteResponse> registerUser(
    @RequestBody @Valid final GeneralRegisterRequest generalRegisterRequest) {
    RegisterCompleteResponse registerCompleteResponse = userService.registerUser(
      generalRegisterRequest);

    return ResponseEntity.ok(registerCompleteResponse);
  }

  @PostMapping(value = "/register/{providerName}")
  public ResponseEntity<RegisterCompleteResponse> registerOauth2User(
    @PathVariable String providerName,
    @RequestBody Oauth2RegisterRequest oauth2RegisterRequest
  ) {

    RegisterCompleteResponse registerCompleteResponse = oauth2Service.registerOauth2User(
      providerName, oauth2RegisterRequest);

    return ResponseEntity.ok(registerCompleteResponse);
  }

  @GetMapping("")
  public ResponseEntity<List<UserDto>> getUser() {
    List<UserDto> users = userService.getUsers();

    return ResponseEntity.ok(users);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
    UserDto user = userService.getUsers().stream()
      .filter(u -> u.getId().equals(userId))
      .findFirst()
      .orElseThrow(() -> new RuntimeException("User not found"));

    return ResponseEntity.ok(user);
  }

  @GetMapping("/me")
  public ResponseEntity<UserDto> getCurrentUser(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {

    return ResponseEntity.ok(
      userService.getUserByLoginId(securityUserDetails.getUsername())
    );
  }

  @PatchMapping("/me/teacher-profile")
  public ResponseEntity<TeacherProfileDto> patchTeacherProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @RequestBody TeacherProfileRequest teacherProfileRequest
  ) {

    TeacherProfileDto teacherProfileDto = userService.patchTeacherProfile(
      securityUserDetails.getUsername(), teacherProfileRequest
    );

    return ResponseEntity.ok(teacherProfileDto);
  }

  @PatchMapping(value = "/me/default-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UserDto> patchDefaultProfile(
    @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
    @ModelAttribute DefaultProfileRequest defaultProfileRequest
  ) {

    UserDto updatedUser = userService.patchDefaultProfile(
      securityUserDetails.getUsername(), defaultProfileRequest
    );

    return ResponseEntity.ok(updatedUser);
  }

  @PatchMapping("/me/student-profile")
  public ResponseEntity<UserDto> patchTeacherProfile(@RequestBody UserDto userDto) {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (!(principal instanceof Long userId)) {
      throw new RuntimeException("인증된 사용자 정보를 찾을 수 없습니다.");
    }

    return ResponseEntity.ok(null);
  }

  @DeleteMapping("")
  public ResponseEntity<Void> deleteUser() {
    userService.deleteUsers();

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/callback/{providerName}")
  @Hidden
  public Oauth2ProfileResponse oauth2AuthorizationCallback(
    @PathVariable String providerName,
    String code
  ) {

    return oauth2Service.getOauth2Profile(providerName, code);
  }

  @PostMapping("/token")
  public ResponseEntity<JwtPairDto> createToken(@RequestBody GeneralLoginRequest request) {

    JwtPairDto jwtPairDto = userService.generateJwt(request.getLoginId(), request.getPassword());

    return ResponseEntity.ok(jwtPairDto);
  }

  @PostMapping("/token/refresh")
  public JwtPairDto refreshToken(@RequestBody TokenRefreshRequest request) {

    return oauth2Service.refreshToken(request.getRefreshToken());
  }

  @GetMapping("/oauth2-authorization-urls")
  public ResponseEntity<Map<String, Oauth2ProviderDto>> getAuthorizationUrls() {

    return ResponseEntity.ok(oauth2Service.generateAuthorizationUrls());
  }

}
