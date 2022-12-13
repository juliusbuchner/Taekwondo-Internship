package se.taekwondointernship.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.taekwondointernship.data.models.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
