package promiseofblood.umpabackend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.Instrument;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.RegionCategory;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;
import promiseofblood.umpabackend.dto.ConstantDto;

@RestController
@RequestMapping("api/v1/constants")
@Tag(name = "상수 API", description = "서버에서 필요한 상수 값들을 조회하는 API")
public class ConstantsController {

  @GetMapping("/majors")
  public ResponseEntity<List<ConstantDto.MajorResponse>> getMajors() {

    List<ConstantDto.MajorResponse> majorResponses = Stream.of(Major.values())
      .map(ConstantDto.MajorResponse::from)
      .collect(Collectors.toList());

    return ResponseEntity.ok(majorResponses);
  }

  @GetMapping("/weekdays")
  public ResponseEntity<List<ConstantDto.WeekdayResponse>> getWeekDays() {

    List<ConstantDto.WeekdayResponse> weekdayResponses = new ArrayList<>();
    for (WeekDay weekday : WeekDay.values()) {
      weekdayResponses.add(ConstantDto.WeekdayResponse.from(weekday));
    }

    return ResponseEntity.ok(weekdayResponses);
  }

  @GetMapping("/colleges")
  public ResponseEntity<List<ConstantDto.CollegeResponse>> getColleges() {

    List<ConstantDto.CollegeResponse> collegeResponses = new ArrayList<>();
    for (College college : College.values()) {
      collegeResponses.add(ConstantDto.CollegeResponse.from(college));
    }

    return ResponseEntity.ok(collegeResponses);
  }

  @GetMapping("/subjects")
  public ResponseEntity<List<ConstantDto.SubjectResponse>> getSubjects() {

    List<ConstantDto.SubjectResponse> subjectResponses = new ArrayList<>();
    for (Subject subject : Subject.values()) {
      subjectResponses.add(ConstantDto.SubjectResponse.of(subject));
    }

    return ResponseEntity.ok(subjectResponses);
  }

  @GetMapping("lessonStyles")
  public ResponseEntity<List<ConstantDto.LessonStyleResponse>> getLessonStyles() {

    List<ConstantDto.LessonStyleResponse> lessonStyleResponses = new ArrayList<>();
    for (LessonStyle lessonStyle : LessonStyle.values()) {
      lessonStyleResponses.add(ConstantDto.LessonStyleResponse.from(lessonStyle));
    }

    return ResponseEntity.ok(lessonStyleResponses);
  }


  @GetMapping("/grades")
  public ResponseEntity<List<ConstantDto.GradeResponse>> getGrades() {

    List<ConstantDto.GradeResponse> gradeResponses = new ArrayList<>();
    for (Grade grade : Grade.values()) {
      gradeResponses.add(ConstantDto.GradeResponse.from(grade));
    }

    return ResponseEntity.ok(gradeResponses);

  }

  @GetMapping("/regions")
  public ResponseEntity<List<ConstantDto.RegionCategoryResponse>> getRegions() {

    List<ConstantDto.RegionCategoryResponse> regionCategories = Stream.of(RegionCategory.values())
      .map(ConstantDto.RegionCategoryResponse::from).toList();

    return ResponseEntity.ok(regionCategories);
  }

  @GetMapping("/instruments")
  public ResponseEntity<List<ConstantDto.InstrumentResponse>> getInstruments() {

    List<ConstantDto.InstrumentResponse> instrumentResponses = new ArrayList<>();
    for (Instrument instrument : Instrument.values()) {
      instrumentResponses.add(ConstantDto.InstrumentResponse.from(instrument));
    }

    return ResponseEntity.ok(instrumentResponses);
  }
}
