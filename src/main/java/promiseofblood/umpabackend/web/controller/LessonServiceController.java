package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.command.CreateLessonServicePostCommand;
import promiseofblood.umpabackend.application.command.CreateLessonServicePostCommand.CreateLessonCurriculumCommand;
import promiseofblood.umpabackend.application.query.RetrieveLessonServicePostQuery;
import promiseofblood.umpabackend.application.service.LessonService;
import promiseofblood.umpabackend.application.service.ServiceBoardService;
import promiseofblood.umpabackend.dto.ServicePostDto.ServicePostResponse;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.request.CreateLessonServicePostRequest;
import promiseofblood.umpabackend.web.schema.response.ApiResponse.PaginatedResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveLessonServicePostResponse;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@Tag(name = "서비스 관리 API(레슨)")
public class LessonServiceController {

  private final LessonService lessonService;
  private final ServiceBoardService serviceBoardService;

  @PostMapping(value = "/lesson", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<RetrieveLessonServicePostResponse> registerLesson(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @Validated @ModelAttribute CreateLessonServicePostRequest request) {

    List<CreateLessonCurriculumCommand> curriculumCommands = new ArrayList<>();
    for (String oneLineValue : request.getCurriculums()) {
      curriculumCommands.add(CreateLessonCurriculumCommand.of(oneLineValue));
    }

    CreateLessonServicePostCommand command =
        CreateLessonServicePostCommand.builder()
            .loginId(securityUserDetails.getLoginId())
            .thumbnailImage(request.getThumbnailImage())
            .title(request.getTitle())
            .description(request.getDescription())
            .serviceCostValue(request.getCost())
            .serviceCostUnit("시간")
            .subject(request.getSubject())
            .availableRegions(request.getAvailableRegions())
            .availableWeekDays(request.getAvailableWeekDays())
            .lessonStyle(request.getLessonStyle())
            .isDemoLessonProvided(request.getIsDemoLessonProvided())
            .demoLessonCost(request.getDemoLessonCost())
            .curriculums(curriculumCommands)
            .recommendedTargets(request.getRecommendedTargets())
            .studioPhotos(request.getStudioPhotos())
            .build();

    var post = lessonService.createLessonServicePost(command);

    return ResponseEntity.ok(post);
  }

  @GetMapping("/lesson")
  public ResponseEntity<PaginatedResponse<ServicePostResponse>> getAllLessonServices(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") @Min(value = 1, message = "size는 0보다 커야 합니다.") int size) {

    Page<ServicePostResponse> servicePostResponsePage =
        this.serviceBoardService.getAllServices("LESSON", page, size);

    return ResponseEntity.ok(PaginatedResponse.from(servicePostResponsePage));
  }

  @GetMapping("/lesson/{id}")
  public ResponseEntity<RetrieveLessonServicePostResponse> retrieveLessonServicePost(
      @PathVariable Long id) {

    var query = new RetrieveLessonServicePostQuery(id);
    var lessonServicePost = lessonService.retrieveLessonServicePost(query);

    return ResponseEntity.ok(lessonServicePost);
  }
}
