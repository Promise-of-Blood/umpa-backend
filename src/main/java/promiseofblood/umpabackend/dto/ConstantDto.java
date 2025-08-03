package promiseofblood.umpabackend.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.College;


public class ConstantDto {

//  public static interface EnumWrapper<T extends Enum<T>> {
//    String getCode();
//
//    String getName();
//
//  }

  @Getter
  @Builder(access = AccessLevel.PRIVATE)
  public static class CollegeResponse {

    private String code;

    private String name;

    public static CollegeResponse from(College college) {
      return CollegeResponse.builder()
        .code(college.name())
        .name(college.getKoreanName())
        .build();
    }
  }
}
