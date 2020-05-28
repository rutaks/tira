package rw.rutaks.tira.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rw.rutaks.tira.exception.OperationFailedException;
import rw.rutaks.tira.model.Admin;
import rw.rutaks.tira.model.Auth;
import rw.rutaks.tira.model.Person;
import rw.rutaks.tira.model.User;
import rw.rutaks.tira.repo.AdminRepo;
import rw.rutaks.tira.repo.AuthRepo;
import rw.rutaks.tira.repo.UserRepo;

@Component
@Slf4j
public class UserSeeders {
  private static final String TAG = "Seeders";

  private static final List<String> normalRole = new ArrayList<>(Arrays.asList("ROLE_USER"));
  private static final List<String> adminRole = new ArrayList<>(Arrays.asList("ROLE_ADMIN"));

  @Autowired PasswordEncoder passwordEncoder;
  @Autowired private UserRepo userRepo;
  @Autowired private AdminRepo adminRepo;
  @Autowired private AuthRepo authRepo;

  public boolean seed() {
    try {
      this.seedUsersTable();
      return true;
    } catch (Exception e) {
      log.error(TAG, e.getMessage());
      return false;
    }
  }

  public void seedUsersTable() {
    try {
      Admin admin = new Admin("Admin", "One", "male", new Date(), "rutaksam@gmail.com");
      adminRepo.save(admin);
      Auth auth1 = new Auth(admin, "user1", passwordEncoder.encode("Password123!"), adminRole);
      authRepo.save(auth1);

      User user = new User("User", "Two", "male", new Date(), "rootsum.dev@gmail.com");
      userRepo.save(user);
      Auth auth2 = new Auth(user, "user2", passwordEncoder.encode("Password123!"), normalRole);
      authRepo.save(auth2);
    } catch (Exception e) {
      throw new OperationFailedException("Could Not Run User Seeds");
    }
  }
}
