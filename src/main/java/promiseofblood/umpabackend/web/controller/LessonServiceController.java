package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.command.CreateLessonServicePostCommand;

import promiseofblood.umpabackend.application.command.CreateLessonServicePostCommand.CreateLessonCurriculumCommand;
import promiseofblood.umpabackend.application.service.LessonService;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.request.CreateLessonServicePostRequest;
import promiseofblood.umpabackend.web.schema.response.RetrieveLessonServicePostResponse;


@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class LessonServiceController {

  private final LessonService lessonService;

  @Tag(name = "서비스 관리 API(레슨)")
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

  @Tag(name = "서비스 관리 API(레슨)")
  @GetMapping("/lesson/{id}")
  @PreAuthorize("isAuthenticated()")
  public void list() {}
}
