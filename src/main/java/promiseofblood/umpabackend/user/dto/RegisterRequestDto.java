package promiseofblood.umpabackend.user.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RegisterRequestDto {
  @NotBlank
  @Size(min = 2, max = 20)
  @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "이름은 영문, 숫자, 한글만 가능합니다.")
  private String name;

  @NotBlank
  private Long majorId;

  @Size(min = 3, max = 3)
  private List<Long> collegeIds;

  @NotBlank
  private String accessToken;

  @NotBlank
  private String refreshToken;
}
