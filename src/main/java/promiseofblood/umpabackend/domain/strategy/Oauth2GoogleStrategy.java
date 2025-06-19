package promiseofblood.umpabackend.domain.strategy;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.domain.vo.Oauth2Provider;
import promiseofblood.umpabackend.dto.external.Oauth2ProfileResponse;
import promiseofblood.umpabackend.dto.external.Oauth2TokenResponse;

@Component
@ToString
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
  public Oauth2TokenResponse getToken(Oauth2Provider oauth2Provider, String authorizationCode) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("grant_type", "authorization_code");
    body.add("client_id", oauth2Provider.getClientId());
    body.add("redirect_uri", oauth2Provider.getRedirectUri());
    body.add("client_secret", oauth2Provider.getClientSecret());
    body.add("code", authorizationCode);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    return restTemplate.postForObject(
      oauth2Provider.getTokenUri(),
      new HttpEntity<>(body, headers),
      Oauth2TokenResponse.class
    );

  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(
    Oauth2Provider oauth2Provider, String authorizationCode
  ) {
    Oauth2TokenResponse oauth2TokenResponse = this.getToken(oauth2Provider, authorizationCode);

    GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
      new GsonFactory())
      .setAudience(Collections.singletonList(oauth2Provider.getClientId()))
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
      return null;
    }
  }

  @Override
  public Oauth2ProfileResponse getOauth2UserProfile(
    Oauth2Provider oauth2Provider, String externalAccessToken, String externalIdToken
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
