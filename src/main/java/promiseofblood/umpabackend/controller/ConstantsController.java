package promiseofblood.umpabackend.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
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
public class ConstantsController {

  @GetMapping("/majors")
  public List<MajorResponse> getMajors() {

    return Stream.of(Major.values())
      .map(Major -> MajorResponse.builder()
        .code(Major.name())
        .name(Major.getKoreanName())
        .build())
      .collect(Collectors.toList());
  }

  @GetMapping("/weekdays")
  public List<WeekdayResponse> getWeekDays() {

    return Stream.of(WeekDay.values())
      .map(weekDay -> WeekdayResponse.builder()
        .code(weekDay.name())
        .name(weekDay.getKoreanName())
        .build())
      .collect(Collectors.toList());
  }

  @GetMapping("/colleges")
  public List<CollegeResponse> getColleges() {

    return Stream.of(College.values())
      .map(college -> CollegeResponse.builder()
        .code(college.name())
        .name(college.getKoreanName())
        .build())
      .collect(Collectors.toList());
  }

  @GetMapping("/subjects")
  public List<SubjectResponse> getSubjects() {

    return Stream.of(Subject.values())
      .map(college -> SubjectResponse.builder()
        .code(college.name())
        .name(college.getKoreanName())
        .build())
      .collect(Collectors.toList());
  }

  @GetMapping("lessonStyles")
  public List<LessonStyleResponse> getLessonStyles() {

    return Stream.of(LessonStyle.values())
      .map(lessonStyle -> LessonStyleResponse.builder()
        .code(lessonStyle.name())
        .name(lessonStyle.getKoreanName())
        .build())
      .collect(Collectors.toList());
  }


  @GetMapping("/grades")
  public List<GradeResponse> getGrades() {

    return Stream.of(Grade.values())
      .map(grade -> GradeResponse.builder()
        .code(grade.name())
        .name(grade.getKoreanName())
        .build())
      .collect(Collectors.toList());
  }

  @GetMapping("/regions")
  public List<RegionCategoryDto> getRegions() {

    return Stream.of(RegionCategory.values())
      .map(regionCategory -> RegionCategoryDto.builder()
        .code(regionCategory.getCode())
        .name(regionCategory.getKoreanName())
        .regions(
          regionCategory.getRegions().stream()
            .map(region -> RegionDto.builder()
              .code(region.getCode())
              .name(region.getKoreanName())
              .build())
            .collect(Collectors.toList())
        )
        .build()
      )
      .collect(Collectors.toList());
  }

}
