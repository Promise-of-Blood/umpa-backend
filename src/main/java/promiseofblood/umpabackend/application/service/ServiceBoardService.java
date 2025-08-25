package promiseofblood.umpabackend.application.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.SampleMrUrl;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.ServicePostRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.dto.ServicePostDto;
import promiseofblood.umpabackend.web.schema.request.CreateAccompanimentServicePostRequest;
import promiseofblood.umpabackend.web.schema.request.CreateMrProductionServicePostRequest;
import promiseofblood.umpabackend.web.schema.request.CreateScoreProductionServicePostRequest;
import promiseofblood.umpabackend.web.schema.response.RetrieveAccompanimentServicePostResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveMrProductionServiceResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveScoreProductionServiceResponse;

@Service
@RequiredArgsConstructor
public class ServiceBoardService {

  private final StorageService storageService;
  private final UserRepository userRepository;
  private final ServicePostRepository servicePostRepository;

  @Transactional
  public Page<ServicePostDto.ServicePostResponse> getAllServices(
    String serviceType, int page, int size) {
    Page<ServicePost> servicePostPage =
      servicePostRepository.findAllByServiceType(serviceType, PageRequest.of(page, size));

    return servicePostPage.map(
      servicePost -> {
        User teacherUser =
          userRepository
            .findById(servicePost.getUser().getId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return ServicePostDto.ServicePostResponse.builder()
          .id(servicePost.getId())
          .title(servicePost.getTitle())
          .tags(List.of("기타", "보컬"))
          .teacherName(teacherUser.getUsername().getValue())
          .thumbnailImageUrl(servicePost.getThumbnailImageUrl())
          .costAndUnit(servicePost.getCostAndUnit())
          .reviewRating(5.0f)
          .build();
      });
  }

  @Transactional
  public RetrieveAccompanimentServicePostResponse
  createAccompanimentServicePost(
    String loginId,
    CreateAccompanimentServicePostRequest createAccompanimentServicePostRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath =
      storageService.store(
        createAccompanimentServicePostRequest.getThumbnailImage(),
        "service/" + user.getId() + "/mr-production");

    AccompanimentServicePost accompanimentServicePost =
      AccompanimentServicePost.builder()
        .user(user)
        .title(createAccompanimentServicePostRequest.getTitle())
        .description(createAccompanimentServicePostRequest.getDescription())
        .thumbnailImageUrl(filePath)
        .serviceCost(
          ServiceCost.builder().cost(createAccompanimentServicePostRequest.getCost()).unit("학교")
            .build())
        .additionalCostPolicy(createAccompanimentServicePostRequest.getAdditionalCostPolicy())
        .instrument(createAccompanimentServicePostRequest.getInstrument())
        .includedPracticeCount(createAccompanimentServicePostRequest.getIncludedPracticeCount())
        .additionalPracticeCost(createAccompanimentServicePostRequest.getAdditionalPracticeCost())
        .isMrIncluded(createAccompanimentServicePostRequest.getIsMrIncluded())
        .practiceLocation(createAccompanimentServicePostRequest.getPracticeLocation())
        .videoUrls(createAccompanimentServicePostRequest.getVideoUrls())
        .build();
    servicePostRepository.save(accompanimentServicePost);

    return RetrieveAccompanimentServicePostResponse.from(
      accompanimentServicePost, user);
  }

  @Transactional
  public RetrieveScoreProductionServiceResponse createScoreProductionServicePost(
    String loginId,
    CreateScoreProductionServicePostRequest scoreProductionRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String thumbnailFilePath =
      storageService.store(
        scoreProductionRequest.getThumbnailImage(),
        "service/" + user.getId() + "/score-production");

    String sampleScoreFilePath =
      storageService.store(
        scoreProductionRequest.getSampleScoreImage(),
        "service/" + user.getId() + "/score-production/sample-score");

    List<ServiceCost> serviceCosts = new ArrayList<>();
    for (String costByScoreType : scoreProductionRequest.getCostByScoreType().split(",")) {
      String[] parts = costByScoreType.split("-");
      String scoreType = parts[0];
      int cost = Integer.parseInt(parts[1]);
      serviceCosts.add(ServiceCost.builder().unit(scoreType).cost(cost).build());
    }

    ScoreProductionServicePost scoreProductionServicePost =
      ScoreProductionServicePost.builder()
        .user(user)
        .title(scoreProductionRequest.getTitle())
        .thumbnailImageUrl(thumbnailFilePath)
        .description(scoreProductionRequest.getDescription())
        .serviceCosts(serviceCosts)
        .additionalCostPolicy(scoreProductionRequest.getAdditionalCostPolicy())
        .freeRevisionCount(scoreProductionRequest.getFreeRevisionCount())
        .additionalRevisionCost(scoreProductionRequest.getAdditionalRevisionCost())
        .averageDuration(DurationRange.of(scoreProductionRequest.getAverageDuration()))
        .softwareUsed(scoreProductionRequest.getSoftwareUsed())
        .sampleScoreImageUrl(sampleScoreFilePath)
        .build();
    servicePostRepository.save(scoreProductionServicePost);

    return RetrieveScoreProductionServiceResponse.from(
      scoreProductionServicePost);
  }

  @Transactional(readOnly = true)
  public RetrieveScoreProductionServiceResponse getScoreProductionServicePost(
    Long id) {

    ScoreProductionServicePost mrProductionServicePost =
      (ScoreProductionServicePost) servicePostRepository.findById(id).get();

    return RetrieveScoreProductionServiceResponse.from(
      mrProductionServicePost
    );
  }

  @Transactional
  public RetrieveMrProductionServiceResponse createMrProductionServicePost(
    String loginId, CreateMrProductionServicePostRequest createMrProductionServicePostRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath =
      storageService.store(
        createMrProductionServicePostRequest.getThumbnailImage(),
        "service/" + user.getId() + "/mr-production");

    MrProductionServicePost mrProductionServicePost =
      MrProductionServicePost.builder()
        .user(user)
        .title(createMrProductionServicePostRequest.getTitle())
        .thumbnailImageUrl(filePath)
        .description(createMrProductionServicePostRequest.getDescription())
        .serviceCost(
          ServiceCost.builder().cost(createMrProductionServicePostRequest.getCost()).unit("곡")
            .build())
        .additionalCostPolicy(createMrProductionServicePostRequest.getAdditionalCostPolicy())
        .freeRevisionCount(createMrProductionServicePostRequest.getFreeRevisionCount())
        .averageDuration(
          DurationRange.of(createMrProductionServicePostRequest.getAverageDuration()))
        .softwareUsed(createMrProductionServicePostRequest.getSoftwareUsed())
        .sampleMrUrls(
          createMrProductionServicePostRequest.getSampleMrUrls().stream().map(SampleMrUrl::of)
            .toList())
        .build();
    servicePostRepository.save(mrProductionServicePost);

    return RetrieveMrProductionServiceResponse.of(mrProductionServicePost);
  }

  @Transactional(readOnly = true)
  public RetrieveMrProductionServiceResponse getMrProductionServicePost(Long id) {

    MrProductionServicePost mrProductionServicePost =
      (MrProductionServicePost) servicePostRepository.findById(id).get();

    return RetrieveMrProductionServiceResponse.of(mrProductionServicePost);
  }
}
