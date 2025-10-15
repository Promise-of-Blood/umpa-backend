package promiseofblood.umpabackend.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import promiseofblood.umpabackend.domain.entity.ChatMessage;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
  List<ChatMessage> findByChatRoomId(Long chatRoomId);
}
