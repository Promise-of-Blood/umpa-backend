package promiseofblood.umpabackend.dto.response.common;


import java.util.List;

public class PaginatedApiResponse<T> extends ApiResponse<T> {

  private int count;

  private int page;

  private int pageSize;

  private String previousPage;

  private String nextPage;

  private List<T> data;

}
