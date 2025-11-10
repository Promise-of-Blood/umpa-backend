package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;

@Entity
@Getter
@Table(name = "chat_messages")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage extends TimeStampedEntity {

  @ManyToOne
  @JoinColumn(name = "chat_room_id")
  private ChatRoom chatRoom;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;

  private String message;

  public static ChatMessage create(ChatRoom chatRoom, User sender, String message) {
    return ChatMessage.builder().chatRoom(chatRoom).sender(sender).message(message).build();
  }
}
