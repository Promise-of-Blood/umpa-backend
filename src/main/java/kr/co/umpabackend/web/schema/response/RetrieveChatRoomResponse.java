package kr.co.umpabackend.web.schema.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetrieveChatRoomResponse {

  private Long id;
  private Long servicePostId;
  private String teacherName;
  private String studentName;
}
