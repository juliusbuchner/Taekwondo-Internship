package se.taekwondointernship.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.taekwondointernship.data.models.entity.CreatePass;

public interface CreatePassRepository extends JpaRepository<CreatePass,Integer> {
}
