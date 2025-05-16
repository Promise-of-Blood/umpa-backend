package promiseofblood.umpabackend.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
@AllArgsConstructor
public class Oauth2RegisterRequest {
  /*
   * 1. 닉네임
   * 2. 전공
   * 3. 희망 학교 (3개)
   * 4. 프로필 입력
   *  - 프로필사진
   *  - 학년 (중3 고1 고2 고3, n수생, 대학생, 사회인)
   *  - 성별 (남자, 여자)
   * 5. 희망 과목 <- 한가지만 선택가능
   * 6. 수업 요청서
   *  - 수업방식(대면, 비대면)
   *  - 레슨 가능한 요일
   *  - 원하는 수업 방향(텍스트)
   */

  private String username;

  private String major;

  private List<String> preferredColleges;

  private StudentProfileRequest studentProfileRequest;

  static class StudentProfileRequest {

    private String profileImage;

    private String grade;

    private String gender;
    
  }
}
