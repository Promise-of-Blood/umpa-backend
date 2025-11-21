package kr.co.umpabackend.web.schema.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshJwtRequest {

  @NotBlank private final String refreshToken;
}
