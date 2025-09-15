package promiseofblood.umpabackend.application.query;

import lombok.Builder;

public record ConstantListQuery() {

  @Builder
  public record Result(String code, String name, String svg) {}
}
