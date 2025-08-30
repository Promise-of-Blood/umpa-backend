package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;
import promiseofblood.umpabackend.web.schema.request.PatchTeacherProfileRequest;

@Entity
@Getter
@Setter
@SuperBuilder
@ToString
@Table(name = "teacher_profile_careers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TeacherCareer extends TimeStampedEntity {

  private boolean isRepresentative;

  private String title;

  @Convert(converter = YearMonthConverter.class)
  private YearMonth start;

  @Column(name = "\"end\"")
  @Convert(converter = YearMonthConverter.class)
  private YearMonth end;

  @ManyToOne
  @JoinColumn(name = "teacher_profile_id")
  private TeacherProfile teacherProfile;

  public static TeacherCareer from(PatchTeacherProfileRequest.TeacherCareerRequest request) {
    return TeacherCareer.builder()
        .isRepresentative(request.isRepresentative())
        .title(request.getTitle())
        .start(request.getStart())
        .end(request.getEnd())
        .build();
  }

  @Converter(autoApply = true)
  static class YearMonthConverter implements AttributeConverter<YearMonth, String> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

    @Override
    public String convertToDatabaseColumn(YearMonth yearMonth) {
      return yearMonth.format(formatter);
    }

    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
      return YearMonth.parse(dbData, formatter);
    }
  }
}
