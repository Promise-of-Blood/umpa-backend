package promiseofblood.umpabackend.web.schema.response;

import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.dto.ServicePostDto.CostPerUnitDto;
import promiseofblood.umpabackend.dto.ServicePostDto.TeacherAuthorProfileDto;

@Getter
@Builder
public class RetrieveLessonServicePostResponse {

  private long id;

  private String thumbnailImage;

  private String title;

  private String description;

  private CostPerUnitDto costPerUnit;

  private TeacherAuthorProfileDto teacherProfile;

  private float reviewRating;
}
