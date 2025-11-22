package kr.co.umpabackend.web.schema.response;

import kr.co.umpabackend.domain.vo.EnumVoType;
import lombok.Getter;

@Getter
public class ConstantResponse<T extends EnumVoType> {

  private final String name;
  private final String code;

  public ConstantResponse(T enumVoType) {
    this.name = enumVoType.getName();
    this.code = enumVoType.getCode();
  }
}
