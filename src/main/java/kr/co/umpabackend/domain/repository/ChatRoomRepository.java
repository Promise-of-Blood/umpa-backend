package kr.co.umpabackend.domain.repository;

import java.util.List;
import kr.co.umpabackend.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
  List<ChatRoom> findByStudentIdOrTeacherId(Long studentId, Long teacherId);
}
