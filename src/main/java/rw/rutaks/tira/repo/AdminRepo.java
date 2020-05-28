package rw.rutaks.tira.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.rutaks.tira.model.Admin;
import rw.rutaks.tira.model.User;

@Repository
public interface AdminRepo extends JpaRepository<Admin, Long> {
  Optional<Admin> findByEmail(String email);
}
