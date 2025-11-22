package kr.co.umpabackend.web.schema.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckIsOauth2RegisterAvailableResponse {

  private String providerName;

  private boolean isAvailable;

  private String message;
}
