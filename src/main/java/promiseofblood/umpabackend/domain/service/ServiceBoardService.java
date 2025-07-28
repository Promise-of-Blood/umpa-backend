package promiseofblood.umpabackend.domain.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import promiseofblood.umpabackend.domain.entity.AccompanimentServicePost;
import promiseofblood.umpabackend.domain.entity.DurationRange;
import promiseofblood.umpabackend.domain.entity.MrProductionServicePost;
import promiseofblood.umpabackend.domain.entity.ServiceCost;
import promiseofblood.umpabackend.domain.entity.ServicePost;
import promiseofblood.umpabackend.domain.entity.User;
import promiseofblood.umpabackend.dto.AccompanimentServicePostDto;
import promiseofblood.umpabackend.dto.request.MrProductionServicePostRequest;
import promiseofblood.umpabackend.dto.response.MrProductionServicePostResponse;
import promiseofblood.umpabackend.dto.response.ServicePostResponse;
import promiseofblood.umpabackend.repository.ServicePostRepository;
import promiseofblood.umpabackend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ServiceBoardService {

  private final StorageService storageService;
  private final UserRepository userRepository;
  private final ServicePostRepository servicePostRepository;

  @Transactional
  public Page<ServicePostResponse> getAllServices(
    String serviceType,
    int page,
    int size
  ) {
    Page<ServicePost> servicePostPage = servicePostRepository.findAll(
      PageRequest.of(page, size)
    );

    return servicePostPage.map(servicePost -> {
      User teacherUser = userRepository.findById(servicePost.getUser().getId())
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
      return ServicePostResponse.builder()
        .id(servicePost.getId())
        .title(servicePost.getTitle())
        .tags(List.of("기타", "보컬"))
        .teacherName(teacherUser.getUsername())
        .thumbnailImageUrl(servicePost.getThumbnailImageUrl())
        .costAndUnit(servicePost.getCostAndUnit())
        .reviewRating(5.0f)
        .build();
    });

  }

  @Transactional
  public AccompanimentServicePostDto.AccompanimentServicePostResponse createAccompanimentServicePost(
    String loginId,
    AccompanimentServicePostDto.AccompanimentPostRequest accompanimentPostRequest) {

    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath = storageService.store(
      accompanimentPostRequest.getThumbnailImage(),
      "service/" + user.getId() + "/mr-production"
    );

    AccompanimentServicePost accompanimentServicePost = AccompanimentServicePost.builder()
      .user(user)
      .title(accompanimentPostRequest.getTitle())
      .description(accompanimentPostRequest.getDescription())
      .thumbnailImageUrl(filePath)
      .serviceCost(ServiceCost.builder()
        .cost(accompanimentPostRequest.getCost())
        .unit("학교")
        .build())
      .additionalCostPolicy(accompanimentPostRequest.getAdditionalCostPolicy())
      .instrument(accompanimentPostRequest.getInstrument())
      .includedPracticeCount(accompanimentPostRequest.getIncludedPracticeCount())
      .additionalPracticeCost(accompanimentPostRequest.getAdditionalPracticeCost())
      .isMrIncluded(accompanimentPostRequest.getIsMrIncluded())
      .practiceLocation(accompanimentPostRequest.getPracticeLocation())
      .videoUrls(accompanimentPostRequest.getVideoUrls())
      .build();
    servicePostRepository.save(accompanimentServicePost);

    return AccompanimentServicePostDto.AccompanimentServicePostResponse.from(
      accompanimentServicePost, user
    );
  }


  @Transactional
  public MrProductionServicePostResponse createMrProductionServicePost(
    String loginId,
    MrProductionServicePostRequest mrProductionServicePostRequest) {

    User user = userRepository.findByLoginId(loginId)
      .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

    String filePath = storageService.store(
      mrProductionServicePostRequest.getThumbnailImage(),
      "service/" + user.getId() + "/mr-production"
    );

    MrProductionServicePost mrProductionServicePost = MrProductionServicePost.builder()
      .user(user)
      .title(mrProductionServicePostRequest.getTitle())
      .thumbnailImageUrl(filePath)
      .description(mrProductionServicePostRequest.getDescription())
      .serviceCost(
        ServiceCost.builder()
          .cost(mrProductionServicePostRequest.getCost())
          .unit("곡")
          .build()
      )
      .additionalCostPolicy(mrProductionServicePostRequest.getAdditionalCostPolicy())
      .freeRevisionCount(mrProductionServicePostRequest.getFreeRevisionCount())
      .averageDuration(DurationRange.of(mrProductionServicePostRequest.getAverageDuration()))
      .softwareUsed(mrProductionServicePostRequest.getSoftwareUsed())
      .sampleMrUrls(mrProductionServicePostRequest.getSampleMrUrls())
      .build();
    servicePostRepository.save(mrProductionServicePost);

    return MrProductionServicePostResponse.of(mrProductionServicePost);
  }

  @Transactional(readOnly = true)
  public MrProductionServicePostResponse getMrProductionServicePost(Long id) {

    MrProductionServicePost mrProductionServicePost = (MrProductionServicePost) servicePostRepository.findById(
      id).get();

    return MrProductionServicePostResponse.of(mrProductionServicePost);
  }
}
