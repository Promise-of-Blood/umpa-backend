package kr.co.umpabackend.application.service;

import java.util.List;
import kr.co.umpabackend.application.exception.ResourceNotFoundException;
import kr.co.umpabackend.application.exception.UnauthorizedException;
import kr.co.umpabackend.domain.entity.ChatMessage;
import kr.co.umpabackend.domain.entity.ChatRoom;
import kr.co.umpabackend.domain.entity.ServicePost;
import kr.co.umpabackend.domain.entity.User;
import kr.co.umpabackend.domain.repository.ChatMessageRepository;
import kr.co.umpabackend.domain.repository.ChatRoomRepository;
import kr.co.umpabackend.domain.repository.ServicePostRepository;
import kr.co.umpabackend.domain.repository.UserRepository;
import kr.co.umpabackend.web.schema.response.ChatMessageResponse;
import kr.co.umpabackend.web.schema.response.RetrieveChatRoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

  private final ChatRoomRepository chatRoomRepository;
  private final ChatMessageRepository chatMessageRepository;
  private final UserRepository userRepository;
  private final ServicePostRepository servicePostRepository;

  @Transactional
  public RetrieveChatRoomResponse createChatRoom(String loginId, Long servicePostId) {
    User student =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    ServicePost servicePost =
        servicePostRepository
            .findById(servicePostId)
            .orElseThrow(() -> new ResourceNotFoundException("Service post not found"));
    User teacher = servicePost.getUser();

    System.out.println(student);
    System.out.println(teacher);

    ChatRoom chatRoom = ChatRoom.create(servicePost, student, teacher);
    chatRoomRepository.save(chatRoom);

    RetrieveChatRoomResponse response = new RetrieveChatRoomResponse();
    response.setId(chatRoom.getId());
    response.setServicePostId(servicePost.getId());
    response.setTeacherName(teacher.getUsername().getValue());
    response.setStudentName(student.getUsername().getValue());

    return response;
  }

  @Transactional(readOnly = true)
  public List<RetrieveChatRoomResponse> getMyChatRooms(String loginId) {
    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    List<ChatRoom> chatRooms =
        chatRoomRepository.findByStudentIdOrTeacherId(user.getId(), user.getId());

    return chatRooms.stream()
        .map(
            chatRoom -> {
              RetrieveChatRoomResponse response = new RetrieveChatRoomResponse();
              response.setId(chatRoom.getId());
              response.setServicePostId(chatRoom.getServicePost().getId());
              response.setTeacherName(chatRoom.getTeacher().getUsername().getValue());
              response.setStudentName(chatRoom.getStudent().getUsername().getValue());
              return response;
            })
        .toList();
  }

  @Transactional(readOnly = true)
  public List<ChatMessageResponse> getChatMessages(String loginId, Long roomId) {
    User user =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    ChatRoom chatRoom =
        chatRoomRepository
            .findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));

    if (!chatRoom.getStudent().getId().equals(user.getId())
        && !chatRoom.getTeacher().getId().equals(user.getId())) {
      throw new UnauthorizedException("You are not a member of this chat room");
    }

    List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(roomId);

    return chatMessages.stream()
        .map(
            chatMessage -> {
              ChatMessageResponse response = new ChatMessageResponse();
              response.setId(chatMessage.getId());
              response.setSenderName(chatMessage.getSender().getUsername().getValue());
              response.setMessage(chatMessage.getMessage());
              response.setSentAt(chatMessage.getCreatedAt().toString());
              return response;
            })
        .toList();
  }

  @Transactional
  public ChatMessageResponse sendMessage(String loginId, Long roomId, String message) {
    User sender =
        userRepository
            .findByLoginId(loginId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    ChatRoom chatRoom =
        chatRoomRepository
            .findById(roomId)
            .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));

    if (!chatRoom.getStudent().getId().equals(sender.getId())
        && !chatRoom.getTeacher().getId().equals(sender.getId())) {
      throw new UnauthorizedException("You are not a member of this chat room");
    }

    ChatMessage chatMessage = ChatMessage.create(chatRoom, sender, message);
    chatMessageRepository.save(chatMessage);

    ChatMessageResponse response = new ChatMessageResponse();
    response.setId(chatMessage.getId());
    response.setSenderName(sender.getUsername().getValue());
    response.setMessage(chatMessage.getMessage());
    response.setSentAt(chatMessage.getCreatedAt().toString());

    return response;
  }
}
