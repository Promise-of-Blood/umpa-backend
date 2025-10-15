package promiseofblood.umpabackend.dto;

import lombok.Getter;
import lombok.Setter;

public class ChatDto {

  @Getter
  @Setter
  public static class CreateChatRoomRequest {
    private Long servicePostId;
  }

  @Getter
  @Setter
  public static class ChatRoomResponse {
    private Long id;
    private Long servicePostId;
    private String teacherName;
    private String studentName;
  }

  @Getter
  @Setter
  public static class ChatMessageResponse {
    private Long id;
    private String senderName;
    private String message;
    private String sentAt;
  }

  @Getter
  @Setter
  public static class SendMessageRequest {
    private String message;
  }
}
