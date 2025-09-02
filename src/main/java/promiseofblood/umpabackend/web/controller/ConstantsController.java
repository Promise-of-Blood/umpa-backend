package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.ConstantService;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.RegionCategory;
import promiseofblood.umpabackend.domain.vo.WeekDay;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses.InstrumentIconResponse;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses.MajorIconResponse;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses.SubjectIconResponse;

@RestController
@RequestMapping("api/v1/constants")
@Tag(name = "상수 API", description = "서버에서 필요한 상수 값들을 조회하는 API")
@RequiredArgsConstructor
public class ConstantsController {

  private final ConstantService constantService;

  @GetMapping("/majors")
  public ResponseEntity<List<ConstantResponses.MajorIconResponse>> getMajors() {

    List<MajorIconResponse> majorIconResponses =
        constantService.getMajorList().stream().map(MajorIconResponse::from).toList();

    return ResponseEntity.ok(majorIconResponses);
  }

  @GetMapping("/weekdays")
  public ResponseEntity<List<ConstantResponses.WeekdayResponse>> getWeekDays() {

    List<ConstantResponses.WeekdayResponse> weekdayResponses = new ArrayList<>();
    for (WeekDay weekday : WeekDay.values()) {
      weekdayResponses.add(ConstantResponses.WeekdayResponse.from(weekday));
    }

    return ResponseEntity.ok(weekdayResponses);
  }

  @GetMapping("/colleges")
  public ResponseEntity<List<ConstantResponses.CollegeResponse>> getColleges() {

    List<ConstantResponses.CollegeResponse> collegeResponses = new ArrayList<>();
    for (College college : College.values()) {
      collegeResponses.add(ConstantResponses.CollegeResponse.from(college));
    }

    return ResponseEntity.ok(collegeResponses);
  }

  @GetMapping("/subjects")
  public ResponseEntity<List<ConstantResponses.SubjectIconResponse>> getSubjects() {

    List<SubjectIconResponse> subjectIconResponses =
        constantService.getSubjectList().stream().map(SubjectIconResponse::from).toList();

    return ResponseEntity.ok(subjectIconResponses);
  }

  @GetMapping("lessonStyles")
  public ResponseEntity<List<ConstantResponses.LessonStyleResponse>> getLessonStyles() {

    List<ConstantResponses.LessonStyleResponse> lessonStyleResponses = new ArrayList<>();
    for (LessonStyle lessonStyle : LessonStyle.values()) {
      lessonStyleResponses.add(ConstantResponses.LessonStyleResponse.from(lessonStyle));
    }

    return ResponseEntity.ok(lessonStyleResponses);
  }

  @GetMapping("/grades")
  public ResponseEntity<List<ConstantResponses.GradeResponse>> getGrades() {

    List<ConstantResponses.GradeResponse> gradeResponses = new ArrayList<>();
    for (Grade grade : Grade.values()) {
      gradeResponses.add(ConstantResponses.GradeResponse.from(grade));
    }

    return ResponseEntity.ok(gradeResponses);
  }

  @GetMapping("/regions")
  public ResponseEntity<List<ConstantResponses.RegionCategoryResponse>> getRegions() {

    List<ConstantResponses.RegionCategoryResponse> regionCategories =
        Stream.of(RegionCategory.values())
            .map(ConstantResponses.RegionCategoryResponse::from)
            .toList();

    return ResponseEntity.ok(regionCategories);
  }

  @GetMapping("/instruments")
  public ResponseEntity<List<ConstantResponses.InstrumentIconResponse>> getInstruments() {

    List<InstrumentIconResponse> instrumentIconResponses =
        constantService.getInstrumentList().stream().map(InstrumentIconResponse::from).toList();

    return ResponseEntity.ok(instrumentIconResponses);
  }

  @GetMapping("scoreTypes")
  public ResponseEntity<List<ConstantResponses.ScoreTypeIconResponse>> getScoreTypes() {

    List<ConstantResponses.ScoreTypeIconResponse> scoreTypeIconResponses =
        constantService.getScoreTypeList().stream()
            .map(ConstantResponses.ScoreTypeIconResponse::from)
            .toList();

    return ResponseEntity.ok(scoreTypeIconResponses);
  }
}
