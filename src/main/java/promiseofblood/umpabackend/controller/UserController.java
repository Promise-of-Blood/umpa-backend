package promiseofblood.umpabackend.controller;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import promiseofblood.umpabackend.controller.common.RestControllerV1;
import promiseofblood.umpabackend.domain.service.Oauth2LoginService;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.request.Oauth2RegisterRequest;


@RestControllerV1
@RequiredArgsConstructor
public class UserController {

  private final Oauth2LoginService oauth2LoginService;

  @GetMapping("/oauth2/urls")
  public Map<String, String> getAuthorizationUrls() {

    return oauth2LoginService.generateAuthorizationUrls();
  }

  @GetMapping("/oauth2/{provider}/authorize")
  public void getAccessToken(
    @PathVariable String provider,
    Oauth2RegisterRequest oauth2RegisterRequest
  ) {

  }


  @GetMapping("/oauth2/{provider}/callback")
  public Oauth2ProfileResponse getAccessTokenCallback(@PathVariable String provider, String code) {

    return oauth2LoginService.getOauth2Profile(provider, code);
  }
}
