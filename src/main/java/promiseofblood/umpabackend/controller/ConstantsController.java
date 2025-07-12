package promiseofblood.umpabackend.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.domain.vo.College;
import promiseofblood.umpabackend.domain.vo.Grade;
import promiseofblood.umpabackend.domain.vo.LessonStyle;
import promiseofblood.umpabackend.domain.vo.Major;
import promiseofblood.umpabackend.domain.vo.RegionCategory;
import promiseofblood.umpabackend.domain.vo.Subject;
import promiseofblood.umpabackend.domain.vo.WeekDay;
import promiseofblood.umpabackend.dto.RegionCategoryDto;
import promiseofblood.umpabackend.dto.RegionDto;
import promiseofblood.umpabackend.dto.response.CollegeResponse;
import promiseofblood.umpabackend.dto.response.GradeResponse;
import promiseofblood.umpabackend.dto.response.LessonStyleResponse;
import promiseofblood.umpabackend.dto.response.MajorResponse;
import promiseofblood.umpabackend.dto.response.SubjectResponse;
import promiseofblood.umpabackend.dto.response.WeekdayResponse;

@RestController
@RequestMapping("api/v1/constants")
@Tag(name = "상수 API", description = "서버에서 필요한 상수 값들을 조회하는 API")
public class ConstantsController {

  @GetMapping("/majors")
  public ResponseEntity<List<MajorResponse>> getMajors() {

    return ResponseEntity.ok(Stream.of(Major.values())
      .map(major -> MajorResponse.builder().code(major.name()).name(major.getKoreanName()).build())
      .collect(Collectors.toList()));
  }

  @GetMapping("/weekdays")
  public ResponseEntity<List<WeekdayResponse>> getWeekDays() {

    return ResponseEntity.ok(Stream.of(WeekDay.values()).map(
      weekDay -> WeekdayResponse.builder().code(weekDay.name()).name(weekDay.getKoreanName())
        .build()).collect(Collectors.toList()));
  }

  @GetMapping("/colleges")
  public ResponseEntity<List<CollegeResponse>> getColleges() {

    return ResponseEntity.ok(Stream.of(College.values()).map(
      college -> CollegeResponse.builder().code(college.name()).name(college.getKoreanName())
        .build()).collect(Collectors.toList()));
  }

  @GetMapping("/subjects")
  public ResponseEntity<List<SubjectResponse>> getSubjects() {

    return ResponseEntity.ok(Stream.of(Subject.values()).map(
      college -> SubjectResponse.builder().code(college.name()).name(college.getKoreanName())
        .build()).collect(Collectors.toList()));
  }

  @GetMapping("lessonStyles")
  public ResponseEntity<List<LessonStyleResponse>> getLessonStyles() {

    return ResponseEntity.ok(Stream.of(LessonStyle.values()).map(
      lessonStyle -> LessonStyleResponse.builder().code(lessonStyle.name())
        .name(lessonStyle.getKoreanName()).build()).collect(Collectors.toList()));
  }


  @GetMapping("/grades")
  public ResponseEntity<List<GradeResponse>> getGrades() {

    return ResponseEntity.ok(Stream.of(Grade.values())
      .map(grade -> GradeResponse.builder().code(grade.name()).name(grade.getKoreanName()).build())
      .collect(Collectors.toList()));
  }

  @GetMapping("/regions")
  public ResponseEntity<List<RegionCategoryDto>> getRegions() {

    return ResponseEntity.ok(Stream.of(RegionCategory.values()).map(
      regionCategory -> RegionCategoryDto.builder().code(regionCategory.getCode())
        .name(regionCategory.getKoreanName()).regions(regionCategory.getRegions().stream().map(
            region -> RegionDto.builder().code(region.getCode()).name(region.getKoreanName()).build())
          .collect(Collectors.toList())).build()).collect(Collectors.toList()));
  }

}
