package kr.co.umpabackend.application.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import kr.co.umpabackend.application.query.ConstantListQuery.Result;
import kr.co.umpabackend.infrastructure.config.StaticResourcePathConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

class ConstantServiceTest {

  private ConstantService subject;

  @BeforeEach
  void setUp() {
    StaticResourcePathConfig staticResourcePathConfig = new StaticResourcePathConfig();
    subject = new ConstantService(staticResourcePathConfig);
  }

  @Test
  @DisplayName("ConstantService의 getMajorList가 반환하는 SVG 파일이 존재한다.")
  void getMajorList_toEnsureSvgFile() {
    // When
    List<Result> majorList = subject.getMajorList();

    // Then
    String svgPath = majorList.get(0).svg();
    ClassPathResource svgFile = new ClassPathResource(svgPath);
    assertTrue(svgFile.exists());
  }

  @Test
  void getInstrumentList_toEnsureSvgFile() {
    // When
    List<Result> instrumentList = subject.getInstrumentList();

    // Then
    String svgPath = instrumentList.get(0).svg();
    ClassPathResource svgFile = new ClassPathResource(svgPath);
    assertTrue(svgFile.exists());
  }

  @Test
  void getScoreTypeList_toEnsureSvgFile() {
    // When
    List<Result> scoreTypeList = subject.getScoreTypeList();

    // Then
    String svgPath = scoreTypeList.get(0).svg();
    ClassPathResource svgFile = new ClassPathResource(svgPath);
    assertTrue(svgFile.exists());
  }

  @Test
  void getSubjectList_toEnsureSvgFile() {
    // When
    List<Result> subjectList = subject.getSubjectList();

    // Then
    String svgPath = subjectList.get(0).svg();
    ClassPathResource svgFile = new ClassPathResource(svgPath);
    assertTrue(svgFile.exists());
  }
}
