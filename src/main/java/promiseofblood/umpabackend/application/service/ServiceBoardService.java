package promiseofblood.umpabackend.application.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.entity.ScoreProductionServicePost;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.domain.repository.ServicePostRepository;
import promiseofblood.umpabackend.domain.repository.UserRepository;
import promiseofblood.umpabackend.domain.vo.DurationRange;
import promiseofblood.umpabackend.domain.vo.ServiceCost;
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto;
import promiseofblood.umpabackend.dto.ScoreProductionServicePostDto;
import promiseofblood.umpabackend.dto.ServicePostDto;
import promiseofblood.umpabackend.web.schema.response.RetrieveAccompanimentServicePostResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveScoreProductionServicePostResponse;

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
  public RetrieveAccompanimentServicePostResponse createAccompanimentServicePost(
      String loginId,
      AccompanimentServicePostDto.AccompanimentPostRequest accompanimentPostRequest) {

    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath =
        storageService.store(
            accompanimentPostRequest.getThumbnailImage(),
            "service/" + user.getId() + "/mr-production");

    AccompanimentServicePost accompanimentServicePost =
        AccompanimentServicePost.builder()
            .user(user)
            .title(accompanimentPostRequest.getTitle())
            .description(accompanimentPostRequest.getDescription())
            .thumbnailImageUrl(filePath)
            .serviceCost(
                ServiceCost.builder().cost(accompanimentPostRequest.getCost()).unit("학교").build())
            .additionalCostPolicy(accompanimentPostRequest.getAdditionalCostPolicy())
            .instrument(accompanimentPostRequest.getInstrument())
            .includedPracticeCount(accompanimentPostRequest.getIncludedPracticeCount())
            .additionalPracticeCost(accompanimentPostRequest.getAdditionalPracticeCost())
            .isMrIncluded(accompanimentPostRequest.getIsMrIncluded())
            .practiceLocations(accompanimentPostRequest.getPracticeLocations())
            .videoUrls(accompanimentPostRequest.getVideoUrls())
            .build();
    servicePostRepository.save(accompanimentServicePost);

    return RetrieveAccompanimentServicePostResponse.from(accompanimentServicePost, user);
  }

  @Transactional
  public RetrieveScoreProductionServicePostResponse createScoreProductionServicePost(
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
      String[] parts = costByScoreType.split(":");
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
            .usingSoftwareList(scoreProductionRequest.getSoftwareList())
            .sampleScoreImageUrl(sampleScoreFilePath)
            .build();
    servicePostRepository.save(scoreProductionServicePost);

    return RetrieveScoreProductionServicePostResponse.from(scoreProductionServicePost);
  }

  @Transactional(readOnly = true)
  public RetrieveScoreProductionServicePostResponse getScoreProductionServicePost(Long id) {

    ScoreProductionServicePost mrProductionServicePost =
        (ScoreProductionServicePost) servicePostRepository.findById(id).get();

    return RetrieveScoreProductionServicePostResponse.from(mrProductionServicePost);
  }
}
