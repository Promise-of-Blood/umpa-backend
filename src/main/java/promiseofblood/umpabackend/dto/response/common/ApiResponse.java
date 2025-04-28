package promiseofblood.umpabackend.dto.response.common;

import lombok.Data;

@Data
public class ApiResponse<T> {

  private T data;
  
}
