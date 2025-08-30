package promiseofblood.umpabackend.web.schema.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SampleMrUrlResponse {

  @Schema(description = "샘플 MR URL", example = "https://example.com/sample1.mp3")
  @NotBlank
  private String url;

  public static SampleMrUrlResponse from(SampleMrUrl sampleMrUrl) {
    return new SampleMrUrlResponse(sampleMrUrl.getUrl());
  }

  public static List<SampleMrUrlResponse> fromList(List<SampleMrUrl> sampleMrUrls) {
    return sampleMrUrls.stream().map(SampleMrUrlResponse::from).toList();
  }
}
