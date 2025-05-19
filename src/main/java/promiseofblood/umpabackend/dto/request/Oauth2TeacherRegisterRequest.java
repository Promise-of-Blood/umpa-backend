package promiseofblood.umpabackend.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@AllArgsConstructor
public class Oauth2TeacherRegisterRequest {
  /*
   * <--- 회원가입 공통 요소 --->
   * - 닉네임
   * - 전공
   * - 프로필사진
   * - 성별
   *
   * <--- 학생 회원가입 --->
   * - 희망 학교 (3개)
   * - 프로필 입력
   *  - 학년 (중3 고1 고2 고3, n수생, 대학생, 사회인)
   * - 희망 과목 <- 한가지만 선택가능
   * - 수업 요청서
   *  - 수업방식(대면, 비대면)
   *  - 레슨 가능한 요일
   *  - 원하는 수업 방향(텍스트)
   *
   * <--- 선생님 회원가입 --->
   * - 프로필 입력
   *  - 레슨 지역
   *  - 경력 (최대 10개, 대표 경력은 3개까지)
   *   - 제목/기간
   *  - 사이트 링크
   */

  @NotBlank
  @Schema(description = "oauth2 제공자로부터 발급된 액세스 토큰")
  private String externalAccessToken;

  @NotBlank(message = "닉네임은 필수입니다.")
  @Size(min = 2, max = 20, message = "닉네임은 2글자 이상 20글자 이하로 입력해주세요.")
  @Pattern(regexp = "^[가-힣a-zA-Z0-9]+$", message = "닉네임은 한글, 영문(대소문자), 숫자만 사용할 수 있습니다.")
  @Schema(description = "닉네임", example = "Goddessana")
  private String username;

  @NotBlank(message = "전공은 필수입니다.")
  @Schema(description = "전공", example = "BASS")
  private String major;

  @NotBlank(message = "성별은 필수입니다.")
  @Schema(description = "성별", example = "MALE")
  private String gender;

  private TeacherProfileRequest teacherProfileRequest;

}

