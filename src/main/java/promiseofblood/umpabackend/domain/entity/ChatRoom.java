package promiseofblood.umpabackend.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import promiseofblood.umpabackend.domain.entity.abs.TimeStampedEntity;

@Entity
@Getter
@Table(name = "chat_rooms")
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom extends TimeStampedEntity {

  @ManyToOne
  @JoinColumn(name = "service_post_id")
  private ServicePost servicePost;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private User student;

  @ManyToOne
  @JoinColumn(name = "teacher_id")
  private User teacher;

  @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ChatMessage> messages;

  public static ChatRoom create(ServicePost servicePost, User student, User teacher) {
    return ChatRoom.builder().servicePost(servicePost).student(student).teacher(teacher).build();
  }
}
