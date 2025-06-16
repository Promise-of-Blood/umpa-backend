package promiseofblood.umpabackend.domain.strategy;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.external.Oauth2TokenResponse;

@Component
@RequiredArgsConstructor
public class Oauth2GoogleStrategy implements Oauth2Strategy {

  private final RestTemplate restTemplate;

  @Override
  public String getAuthorizationUrl(Oauth2Provider oauth2Provider) {
    return oauth2Provider.getLoginUrl()
      + "?client_id=" + oauth2Provider.getClientId()
      + "&redirect_uri=" + oauth2Provider.getRedirectUri()
      + "&response_type=code"
      + "&scope=https://www.googleapis.com/auth/userinfo.profile";
  }

  @Override
  public Oauth2TokenResponse getToken(String code, Oauth2Provider oauth2Provider) {

    // x-www-form-urlencoded 바디 생성
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", oauth2Provider.getClientId());
    body.add("redirect_uri", oauth2Provider.getRedirectUri());
    body.add("client_secret", oauth2Provider.getClientSecret());
    body.add("code", code);

    // 헤더 설정 (Content-Type: application/x-www-form-urlencoded)
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    return restTemplate.postForObject(
      oauth2Provider.getTokenUri(),
      new HttpEntity<>(body, headers),
      Oauth2TokenResponse.class
    );
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(String code,
    Oauth2Provider oauth2Provider) {

    Oauth2TokenResponse oauth2TokenResponse = this.getToken(code, oauth2Provider);
    System.out.println("oauth2TokenResponse = " + oauth2TokenResponse);

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
      new GsonFactory())
      .setAudience(Collections.singletonList(
        "603931353875-q677h2s52f8vs9dne4iqqgmmp44j3ct4.apps.googleusercontent.com"))
      .build();

    try {
      GoogleIdToken idToken = verifier.verify(oauth2TokenResponse.getIdToken());
      Payload payload = idToken.getPayload();
      return Oauth2ProfileResponse.builder()
        .externalIdToken(oauth2TokenResponse.getIdToken())
        .externalAccessToken(oauth2TokenResponse.getAccessToken())
        .providerUid(payload.getSubject())
        .profileImageUrl((String) payload.get("picture"))
        .username((String) payload.get("given_name"))
        .build();

    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfileByIdToken(
    String externalIdToken,
    String externalAccessToken,
    Oauth2Provider oauth2Provider
  ) {

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
      new GsonFactory())
      .setAudience(Collections.singletonList(oauth2Provider.getClientId()))
      .build();

    try {
      GoogleIdToken idToken = verifier.verify(externalIdToken);
      Payload payload = idToken.getPayload();
      return Oauth2ProfileResponse.builder()
        .externalIdToken(externalIdToken)
        .externalAccessToken(externalAccessToken)
        .providerUid(payload.getSubject())
        .profileImageUrl((String) payload.get("picture"))
        .username((String) payload.get("given_name"))
        .build();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }


}
