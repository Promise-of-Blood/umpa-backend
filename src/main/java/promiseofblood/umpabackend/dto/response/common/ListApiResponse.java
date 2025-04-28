package promiseofblood.umpabackend.dto.response.common;


import java.util.List;

public class ListApiResponse<T> {

  private int count;

  private int page;

  private int pageSize;

  private String previousPage;

  private String nextPage;

  private List<T> data;

}
