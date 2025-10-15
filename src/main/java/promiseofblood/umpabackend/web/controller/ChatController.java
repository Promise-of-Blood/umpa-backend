package promiseofblood.umpabackend.web.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import promiseofblood.umpabackend.application.service.ChatService;
import promiseofblood.umpabackend.dto.ChatDto;
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Tag(name = "채팅 API")
public class ChatController {

  private final ChatService chatService;

  @PostMapping("/rooms")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ChatDto.ChatRoomResponse> createChatRoom(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @RequestBody ChatDto.CreateChatRoomRequest request) {
    String loginId = securityUserDetails.getUsername();
    ChatDto.ChatRoomResponse response =
        chatService.createChatRoom(loginId, request.getServicePostId());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/rooms")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<ChatDto.ChatRoomResponse>> getMyChatRooms(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {
    String loginId = securityUserDetails.getUsername();
    List<ChatDto.ChatRoomResponse> response = chatService.getMyChatRooms(loginId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/rooms/{roomId}/messages")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<ChatDto.ChatMessageResponse>> getChatMessages(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails, @PathVariable Long roomId) {
    String loginId = securityUserDetails.getUsername();
    List<ChatDto.ChatMessageResponse> response = chatService.getChatMessages(loginId, roomId);
    return ResponseEntity.ok(response);
  }
}
