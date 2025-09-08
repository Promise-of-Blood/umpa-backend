package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.RegionCategory;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;
import promiseofblood.umpabackend.web.schema.response.ConstantResponses;

@RestController
@RequestMapping("api/v1/constants")
@Tag(name = "상수 API", description = "서버에서 필요한 상수 값들을 조회하는 API")
public class ConstantsController {

  @GetMapping("/majors")
  public ResponseEntity<List<ConstantResponses.MajorResponse>> getMajors() {

    List<ConstantResponses.MajorResponse> majorResponses =
        Stream.of(Major.values())
            .map(ConstantResponses.MajorResponse::from)
            .collect(Collectors.toList());

    return ResponseEntity.ok(majorResponses);
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
  public ResponseEntity<List<ConstantResponses.SubjectResponse>> getSubjects() {

    List<ConstantResponses.SubjectResponse> subjectResponses = new ArrayList<>();
    for (Subject subject : Subject.values()) {
      subjectResponses.add(ConstantResponses.SubjectResponse.from(subject));
    }

    return ResponseEntity.ok(subjectResponses);
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
  public ResponseEntity<List<ConstantResponses.InstrumentResponse>> getInstruments() {

    List<ConstantResponses.InstrumentResponse> instrumentResponses = new ArrayList<>();
    for (Instrument instrument : Instrument.values()) {
      instrumentResponses.add(ConstantResponses.InstrumentResponse.from(instrument));
    }

    return ResponseEntity.ok(instrumentResponses);
  }
}
