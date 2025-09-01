package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.command.CreateLessonServicePostCommand;
import promiseofblood.umpabackend.application.service.LessonService;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.request.CreateLessonServicePostRequest;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class LessonServiceController {

  private final LessonService lessonService;

  // *************
  // * 레슨 서비스 *
  // *************
  @Tag(name = "서비스 관리 API(레슨)")
  @PostMapping(value = "/lesson", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @PreAuthorize("isAuthenticated()")
  public void registerLesson(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @ModelAttribute @Valid CreateLessonServicePostRequest request) {

    CreateLessonServicePostCommand command =
        CreateLessonServicePostCommand.builder()
            .loginId(securityUserDetails.getLoginId())
            .thumbnailImage(request.getThumbnailImage())
            .title(request.getTitle())
            .description(request.getDescription())
            .serviceCostValue(request.getCost())
            .serviceCostUnit("곡")
            .subject(request.getSubject())
            .availableRegions(request.getAvailableRegions())
            .availableWeekDays(request.getAvailableWeekDays())
            .lessonStyle(request.getLessonStyle())
            .isDemoLessonProvided(request.getIsDemoLessonProvided())
            .demoLessonCost(request.getDemoLessonCost())
            .recommendedTargets(request.getRecommendedTargets())
            .studioPhotos(request.getStudioPhotos())
            .build();

    lessonService.createLessonServicePost(command);
  }
}
