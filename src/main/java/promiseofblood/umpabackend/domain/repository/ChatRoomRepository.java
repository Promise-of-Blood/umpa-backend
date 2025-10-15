package promiseofblood.umpabackend.domain.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import promiseofblood.umpabackend.domain.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  List<ChatRoom> findByStudentIdOrTeacherId(Long studentId, Long teacherId);
}
