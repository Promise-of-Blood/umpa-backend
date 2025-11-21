package kr.co.umpabackend.domain.repository;

import java.util.List;
import kr.co.umpabackend.domain.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
