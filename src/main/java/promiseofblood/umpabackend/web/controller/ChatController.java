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
import promiseofblood.umpabackend.infrastructure.security.SecurityUserDetails;
import promiseofblood.umpabackend.web.schema.request.CreateChatRoomRequest;
import promiseofblood.umpabackend.web.schema.response.ChatMessageResponse;
import promiseofblood.umpabackend.web.schema.response.RetrieveChatRoomResponse;

@RestController
@RequestMapping("/api/v1/chat")
@RequiredArgsConstructor
@Tag(name = "채팅 API")
public class ChatController {

  private final ChatService chatService;

  @PostMapping("/rooms")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<RetrieveChatRoomResponse> createChatRoom(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails,
      @RequestBody CreateChatRoomRequest request) {
    String loginId = securityUserDetails.getUsername();
    RetrieveChatRoomResponse response =
        chatService.createChatRoom(loginId, request.getServicePostId());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/rooms")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<RetrieveChatRoomResponse>> getMyChatRooms(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails) {
    String loginId = securityUserDetails.getUsername();
    List<RetrieveChatRoomResponse> response = chatService.getMyChatRooms(loginId);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/rooms/{roomId}/messages")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<List<ChatMessageResponse>> getChatMessages(
      @AuthenticationPrincipal SecurityUserDetails securityUserDetails, @PathVariable Long roomId) {
    String loginId = securityUserDetails.getUsername();
    List<ChatMessageResponse> response = chatService.getChatMessages(loginId, roomId);
    return ResponseEntity.ok(response);
  }
}
