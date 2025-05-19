package promiseofblood.umpabackend.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.domain.service.Oauth2LoginService;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2TeacherRegisterRequest;
import promiseofblood.umpabackend.dto.request.TokenRefreshRequest;
import promiseofblood.umpabackend.dto.response.JwtResponse;


@RestController
@RequestMapping("/api/v1/oauth2")
@RequiredArgsConstructor
public class UserController {

  private final Oauth2LoginService oauth2LoginService;

  @GetMapping("/urls")
  public Map<String, String> getAuthorizationUrls() {

    return oauth2LoginService.generateAuthorizationUrls();
  }

  @PostMapping(value = "/{providerName}/register/teachers", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public Map<String, Object> registerTeacher(
    @PathVariable String providerName,
    @RequestPart Oauth2TeacherRegisterRequest oauth2RegisterRequest
//    @RequestPart MultipartFile profileImage
  ) {
    return oauth2LoginService.oauth2Register(providerName, oauth2RegisterRequest);
  }

  @GetMapping("/{providerName}/callback")
  public Oauth2ProfileResponse getAccessTokenCallback(@PathVariable String providerName,
    String code) {

    return oauth2LoginService.getOauth2Profile(providerName, code);
  }

  @PostMapping("/token/refresh")
  public JwtResponse refreshToken(@RequestBody TokenRefreshRequest request) {
    return oauth2LoginService.refreshToken(request.getRefreshToken());
  }
}
