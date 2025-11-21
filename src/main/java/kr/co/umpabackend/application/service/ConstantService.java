package kr.co.umpabackend.application.service;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import kr.co.umpabackend.application.query.ConstantListQuery;
import kr.co.umpabackend.domain.vo.Instrument;
import kr.co.umpabackend.domain.vo.Major;
import kr.co.umpabackend.domain.vo.ScoreType;
import kr.co.umpabackend.domain.vo.Subject;
import kr.co.umpabackend.infrastructure.config.StaticResourcePathConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConstantService {

  private final StaticResourcePathConfig staticResourcePathConfig;

  public List<ConstantListQuery.Result> getMajorList() {
    return Arrays.stream(Major.values())
        .map(
            major -> {
              Path svgFilePath =
                  staticResourcePathConfig.getSvgPath().resolve(major.getAssetName() + ".svg");

              return ConstantListQuery.Result.builder()
                  .code(major.name())
                  .name(major.getKoreanName())
                  .svg(svgFilePath.toString())
                  .build();
            })
        .toList();
  }

  public List<ConstantListQuery.Result> getInstrumentList() {
    return Arrays.stream(Instrument.values())
        .map(
            instrument -> {
              Path svgFilePath =
                  staticResourcePathConfig.getSvgPath().resolve(instrument.getAssetName() + ".svg");

              return ConstantListQuery.Result.builder()
                  .code(instrument.name())
                  .name(instrument.getKoreanName())
                  .svg(svgFilePath.toString())
                  .build();
            })
        .toList();
  }

  public List<ConstantListQuery.Result> getScoreTypeList() {
    return Arrays.stream(ScoreType.values())
        .map(
            scoreType -> {
              Path svgFilePath =
                  staticResourcePathConfig.getSvgPath().resolve(scoreType.getAssetName() + ".svg");

              return ConstantListQuery.Result.builder()
                  .code(scoreType.name())
                  .name(scoreType.getKoreanName())
                  .svg(svgFilePath.toString())
                  .build();
            })
        .toList();
  }

  public List<ConstantListQuery.Result> getSubjectList() {
    return Arrays.stream(Subject.values())
        .map(
            subject -> {
              Path svgFilePath =
                  staticResourcePathConfig.getSvgPath().resolve(subject.getAssetName() + ".svg");

              return ConstantListQuery.Result.builder()
                  .code(subject.name())
                  .name(subject.getKoreanName())
                  .svg(svgFilePath.toString())
                  .build();
            })
        .toList();
  }
}
