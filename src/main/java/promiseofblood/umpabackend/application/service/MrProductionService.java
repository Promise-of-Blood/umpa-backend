package promiseofblood.umpabackend.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.application.command.CreateMrProductionCommand;
import promiseofblood.umpabackend.application.exception.ResourceNotFoundException;
import promiseofblood.umpabackend.application.query.RetrieveMrServicePostQuery;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.MrProductionServicePostRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.web.schema.MrProductionResponse;

@Service
@RequiredArgsConstructor
public class MrProductionService {

  private final MrProductionServicePostRepository mrProductionServicePostRepository;
  private final UserRepository userRepository;
  private final StorageService storageService;

  @Transactional
  public MrProductionResponse createMrProductionServicePost(CreateMrProductionCommand command) {

    User user = userRepository
      .findByLoginId(command.getLoginId())
      .orElseThrow(() -> new ResourceNotFoundException("사용자를 찾을 수 없습니다."));

    String thumbnailImageFilePath = storageService.store(
      command.getThumbnailImage(),
      "service/" + user.getId() + "/mr-production");

    ServiceCost serviceCost = ServiceCost.of(
      command.getServiceCostValue(),
      command.getServiceCostUnit()
    );

    DurationRange durationRange = DurationRange.of(command.getAverageDuration());

    MrProductionServicePost mrProductionServicePost = MrProductionServicePost.create(
      user,
      thumbnailImageFilePath,
      command.getTitle(),
      command.getDescription(),
      serviceCost,
      command.getAdditionalCostPolicy(),
      command.getFreeRevisionCount(),
      command.getAdditionalRevisionCost(),
      durationRange,
      command.getUsingSoftwareList(),
      command.getSampleMrUrls().stream().map(SampleMrUrl::of).toList()
    );
    mrProductionServicePostRepository.save(mrProductionServicePost);

    return MrProductionResponse.of(mrProductionServicePost);
  }

  @Transactional(readOnly = true)
  public MrProductionResponse retrieveMrProductionServicePost(RetrieveMrServicePostQuery query) {

    MrProductionServicePost mrProductionServicePost = mrProductionServicePostRepository.findById(
        query.id())
      .orElseThrow(() -> new ResourceNotFoundException("해당 ID의 MR 제작 서비스 게시글이 존재하지 않습니다."));

    return MrProductionResponse.of(mrProductionServicePost);
  }
}
