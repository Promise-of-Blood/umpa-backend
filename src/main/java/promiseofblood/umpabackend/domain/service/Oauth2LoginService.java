package promiseofblood.umpabackend.domain.service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import promiseofblood.umpabackend.config.Oauth2ProvidersConfig;
import promiseofblood.umpabackend.domain.entitiy.Oauth2Provider;
import promiseofblood.umpabackend.domain.strategy.Oauth2Strategy;

@Service
@RequiredArgsConstructor
public class Oauth2LoginService {

  private final Oauth2ProvidersConfig oauth2ProvidersConfig;
  private final Map<String, Oauth2Strategy> oauth2Strategies;

  public Map<String, Object> getAccessToken(String providerName, String code) {
    Oauth2Provider provider = oauth2ProvidersConfig.getOauth2ProviderByName(providerName);

    String decodedCode = URLDecoder.decode(code, StandardCharsets.UTF_8);

    // http 요청 보내기(access token 요청)
    RestTemplate restTemplate = new RestTemplate();
    String tokenUrl = provider.getTokenUri();
    String clientId = provider.getClientId();
    String clientSecret = provider.getClientSecret();
    String redirectUri = provider.getRedirectUri();
    String url = tokenUrl
      + "?client_id=" + clientId
      + "&client_secret=" + clientSecret
      + "&redirect_uri=" + redirectUri
      + "&code=" + decodedCode
      + "&grant_type=authorization_code";
    Map<String, Object> response = restTemplate.postForObject(url, null, Map.class);

    // kakao:  response = {"access_token":"...", "token_type":"bearer", "refresh_token":"...","id_token":"...","expires_in":21599,"scope":"profile_image profile_nickname","refresh_token_expires_in":5183999}
    // naver:  response = {"access_token":"...", "refresh_token":"...", "token_type":"bearer","expires_in":"3600"}
    // google: response = {"access_token": "...", "expires_in": 3490, "scope": "https://www.googleapis.com/auth/userinfo.profile", "token_type": "Bearer", "id_token": "..."}
    return response;
  }


  public Map<String, String> generateAuthorizationUrls() {
    Map<String, Oauth2Provider> oauth2Providers = oauth2ProvidersConfig.getOauth2Providers();

    Map<String, String> urls = new java.util.HashMap<>(Map.of());
    for (Oauth2Provider provider : oauth2Providers.values()) {
      String providerName = provider.getName();

      Oauth2Strategy strategy = oauth2Strategies.get(
        "oauth2" +
          providerName.substring(0, 1).toUpperCase() +
          providerName.substring(1) +
          "Strategy"
      );

      if (strategy == null) {
        continue;
      }

      String authorizationUrl = strategy.getAuthorizationUrl(provider);
      urls.put(providerName, authorizationUrl);
    }

    return urls;
  }

}
