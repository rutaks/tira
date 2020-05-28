package rw.rutaks.tira.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.rutaks.tira.model.Account;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {}
