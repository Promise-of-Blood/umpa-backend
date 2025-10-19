package promiseofblood.umpabackend.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import promiseofblood.umpabackend.application.service.ChatService;
import promiseofblood.umpabackend.dto.ChatDto;
import promiseofblood.umpabackend.web.schema.request.SendChatMessageRequest;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

  private final ChatService chatService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/chat.sendMessage/{roomId}")
  public void sendMessage(
      java.security.Principal principal,
      @DestinationVariable Long roomId,
      @Payload SendChatMessageRequest request) {
    String loginId = principal.getName();
    ChatDto.ChatMessageResponse response =
        chatService.sendMessage(loginId, roomId, request.getMessage());
    messagingTemplate.convertAndSend("/topic/chat/" + roomId, response);
  }
}
