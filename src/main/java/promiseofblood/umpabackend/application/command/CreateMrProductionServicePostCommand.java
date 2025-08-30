package promiseofblood.umpabackend.application.command;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class CreateMrProductionServicePostCommand {

  @NotNull
  final String loginId;
  @NotNull
  final String title;
  @NotNull
  final MultipartFile thumbnailImage;
  @NotNull
  final String description;
  @NotNull
  final Integer serviceCostValue;
  @NotNull
  final String serviceCostUnit;
  @NotNull
  final String additionalCostPolicy;
  @NotNull
  final Integer freeRevisionCount;
  @NotNull
  final Integer additionalRevisionCost;
  @NotNull
  final String averageDuration;
  @NotNull
  final List<String> usingSoftwareList;
  @NotNull
  final List<String> sampleMrUrls;
}
