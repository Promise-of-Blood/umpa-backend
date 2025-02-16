package promiseofblood.umpabackend.user.dto.oauth2.naver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class NaverLoginUrlResponseDto {
  private String url;
}
