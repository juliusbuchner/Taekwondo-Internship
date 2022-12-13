package se.taekwondointernship.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.taekwondointernship.data.models.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {
}
