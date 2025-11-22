package kr.co.umpabackend.web.schema.request;

import lombok.Getter;

@Getter
public class DeleteUserRequest {
  private Boolean isHardDelete;
}
