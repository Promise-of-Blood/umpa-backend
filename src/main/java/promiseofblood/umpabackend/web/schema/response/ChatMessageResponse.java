package promiseofblood.umpabackend.web.schema.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageResponse {

  private Long id;
  private String senderName;
  private String message;
  private String sentAt;
}
