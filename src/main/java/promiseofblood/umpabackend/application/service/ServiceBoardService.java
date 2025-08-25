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
import promiseofblood.umpabackend.dto.MrProductionServicePostDto.MrProductionPostRequest;
import promiseofblood.umpabackend.dto.MrProductionServicePostDto.MrProductionResponse;
import promiseofblood.umpabackend.dto.ScoreProductionServicePostDto;
import promiseofblood.umpabackend.dto.ServicePostDto;
import promiseofblood.umpabackend.web.schema.request.AccompanimentServicePostCreateRequest;
import promiseofblood.umpabackend.web.schema.response.AccompanimentServicePostDetailResponse;

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
  public AccompanimentServicePostDetailResponse
  createAccompanimentServicePost(
    String loginId,
    AccompanimentServicePostCreateRequest accompanimentServicePostCreateRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath =
      storageService.store(
        accompanimentServicePostCreateRequest.getThumbnailImage(),
        "service/" + user.getId() + "/mr-production");

    AccompanimentServicePost accompanimentServicePost =
      AccompanimentServicePost.builder()
        .user(user)
        .title(accompanimentServicePostCreateRequest.getTitle())
        .description(accompanimentServicePostCreateRequest.getDescription())
        .thumbnailImageUrl(filePath)
        .serviceCost(
          ServiceCost.builder().cost(accompanimentServicePostCreateRequest.getCost()).unit("학교")
            .build())
        .additionalCostPolicy(accompanimentServicePostCreateRequest.getAdditionalCostPolicy())
        .instrument(accompanimentServicePostCreateRequest.getInstrument())
        .includedPracticeCount(accompanimentServicePostCreateRequest.getIncludedPracticeCount())
        .additionalPracticeCost(accompanimentServicePostCreateRequest.getAdditionalPracticeCost())
        .isMrIncluded(accompanimentServicePostCreateRequest.getIsMrIncluded())
        .practiceLocation(accompanimentServicePostCreateRequest.getPracticeLocation())
        .videoUrls(accompanimentServicePostCreateRequest.getVideoUrls())
        .build();
    servicePostRepository.save(accompanimentServicePost);

    return AccompanimentServicePostDetailResponse.from(
      accompanimentServicePost, user);
  }

  @Transactional
  public ScoreProductionServicePostDto.ScoreProductionServicePostResponse createScoreProductionServicePost(
    String loginId,
    ScoreProductionServicePostDto.ScoreProductionServicePosRequest scoreProductionRequest) {

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

    return ScoreProductionServicePostDto.ScoreProductionServicePostResponse.from(
      scoreProductionServicePost);
  }

  @Transactional(readOnly = true)
  public ScoreProductionServicePostDto.ScoreProductionServicePostResponse getScoreProductionServicePost(
    Long id) {

    ScoreProductionServicePost mrProductionServicePost =
      (ScoreProductionServicePost) servicePostRepository.findById(id).get();

    return ScoreProductionServicePostDto.ScoreProductionServicePostResponse.from(
      mrProductionServicePost
    );
  }

  @Transactional
  public MrProductionResponse createMrProductionServicePost(
    String loginId, MrProductionPostRequest mrProductionPostRequest) {

    User user =
      userRepository
        .findByLoginId(loginId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath =
      storageService.store(
        mrProductionPostRequest.getThumbnailImage(),
        "service/" + user.getId() + "/mr-production");

    MrProductionServicePost mrProductionServicePost =
      MrProductionServicePost.builder()
        .user(user)
        .title(mrProductionPostRequest.getTitle())
        .thumbnailImageUrl(filePath)
        .description(mrProductionPostRequest.getDescription())
        .serviceCost(
          ServiceCost.builder().cost(mrProductionPostRequest.getCost()).unit("곡").build())
        .additionalCostPolicy(mrProductionPostRequest.getAdditionalCostPolicy())
        .freeRevisionCount(mrProductionPostRequest.getFreeRevisionCount())
        .averageDuration(DurationRange.of(mrProductionPostRequest.getAverageDuration()))
        .softwareUsed(mrProductionPostRequest.getSoftwareUsed())
        .sampleMrUrls(
          mrProductionPostRequest.getSampleMrUrls().stream().map(SampleMrUrl::of).toList())
        .build();
    servicePostRepository.save(mrProductionServicePost);

    return MrProductionResponse.of(mrProductionServicePost);
  }

  @Transactional(readOnly = true)
  public MrProductionResponse getMrProductionServicePost(Long id) {

    MrProductionServicePost mrProductionServicePost =
      (MrProductionServicePost) servicePostRepository.findById(id).get();

    return MrProductionResponse.of(mrProductionServicePost);
  }
}
