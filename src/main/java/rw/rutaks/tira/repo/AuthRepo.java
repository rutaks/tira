package rw.rutaks.tira.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.rutaks.tira.model.Auth;
import rw.rutaks.tira.model.Person;
import rw.rutaks.tira.model.ResetPasswordToken;

@Repository
public interface AuthRepo extends JpaRepository<Auth, Long> {
  Optional<Auth> findByUsername(String username);
  Optional<Auth> findByPerson(Person person);
  Optional<Auth> findByResetPasswordTokens(ResetPasswordToken resetPasswordToken);
}
