package promiseofblood.umpabackend.web.schema.response;

import lombok.Getter;
import promiseofblood.umpabackend.domain.vo.EnumVoType;

@Getter
public class ConstantResponse<T extends EnumVoType> {

  private final String name;
  private final String code;

  public ConstantResponse(T enumVoType) {
    this.name = enumVoType.getName();
    this.code = enumVoType.getCode();
  }

  public String getName() {
    return name;
  }

  public String getCode() {
    return code;
  }
}
