package se.taekwondointernship.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.taekwondointernship.data.models.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Integer> {
}
