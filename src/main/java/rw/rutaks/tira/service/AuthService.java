package rw.rutaks.tira.service;

import java.io.IOException;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rw.rutaks.tira.dto.auth.RegisterRequestDTO;
import rw.rutaks.tira.dto.auth.ResetPasswordDTO;
import rw.rutaks.tira.exception.EntityNotFoundException;
import rw.rutaks.tira.exception.MismatchException;
import rw.rutaks.tira.model.Account;
import rw.rutaks.tira.model.Auth;
import rw.rutaks.tira.model.Person;
import rw.rutaks.tira.model.ResetPasswordToken;
import rw.rutaks.tira.model.User;
import rw.rutaks.tira.repo.AccountRepo;
import rw.rutaks.tira.repo.AuthRepo;
import rw.rutaks.tira.repo.ResetPasswordTokenRepo;
import rw.rutaks.tira.repo.UserRepo;
import rw.rutaks.tira.util.DateUtil;

@Service
public class AuthService implements UserDetailsService {

  @Autowired PasswordEncoder passwordEncoder;
  @Autowired private AuthRepo authRepo;
  @Autowired private UserRepo userRepo;
  @Autowired private AccountRepo accountRepo;
  @Autowired private ResetPasswordTokenRepo resetPasswordTokenRepo;
  @Autowired private MailService mailService;

  @Override
  public Auth loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Auth> auth = authRepo.findByUsername(username);
    auth.orElseThrow(() -> new UsernameNotFoundException("Could not find: " + username));
    return auth.get();
  }

  public Person register(RegisterRequestDTO registerRequestDTO) throws Exception {
    try {
      User user = userRepo.save(new User(registerRequestDTO));
      registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
      Auth auth = new Auth(registerRequestDTO, user, new ArrayList<>(Arrays.asList("ROLE_USER")));
      Account account = new Account(user);
      accountRepo.save(account);
      authRepo.save(auth);
      return user;
    } catch (Exception e) {
      throw new Exception("Could Not Create Account: " + e.getMessage());
    }
  }

  public void resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
    resetPasswordTokenRepo.findAll().forEach((r) -> System.out.println(r.getToken()));
    Optional<ResetPasswordToken> resetPasswordToken = resetPasswordTokenRepo.findByToken(token);
    resetPasswordToken.orElseThrow(
        () -> new EntityNotFoundException("Reset Password request not found"));

    if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())) {
      throw new MismatchException("Passwords do not match");
    }
    // TODO: REFACTOR TO COMPARE CURRENT DATE WITH DB DATE. ISSUE IS THAT CURRENT DATE COMES IN
    // `CAT` TIMEZONE AND COMPARISON FAILS
    if (!resetPasswordToken.get().isActive()) {
      throw new DateTimeException("The Token has expired");
    }

    Optional<Auth> auth = authRepo.findByResetPasswordTokens(resetPasswordToken.get());
    auth.orElseThrow(() -> new EntityNotFoundException("Account was not found"));

    auth.get().setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));

    authRepo.save(auth.get());

    resetPasswordToken.get().setActive(false);
    resetPasswordTokenRepo.save(resetPasswordToken.get());
  }

  public void sendForgotPasswordRequest(String email, HttpServletRequest request)
      throws IOException {
    Optional<User> user = userRepo.findByEmail(email);
    user.orElseThrow(() -> new EntityNotFoundException("Could not find user with email: " + email));

    Optional<Auth> auth = authRepo.findByPerson(user.get());
    auth.orElseThrow(
        () -> new EntityNotFoundException("Could not find user auth account with email: " + email));

    ResetPasswordToken resetPasswordToken = new ResetPasswordToken();

    final String token = UUID.randomUUID().toString();
    final String url =
        String.format("%s/reset-password/%s", "http://" + request.getLocalName(), token);

    resetPasswordToken.setToken(token);
    resetPasswordToken.setAuth(auth.get());
    resetPasswordToken.setExpiryDate(DateUtil.addHoursToJavaUtilDate(new Date(), 3));
    resetPasswordToken.setPreviousPassword(auth.get().getPassword());
    resetPasswordTokenRepo.save(resetPasswordToken);

    mailService.sendText(
        "security.test@rutaks.rw ",
        email,
        "Password Reset",
        "Hey "
            + user.get().getFirstName()
            + " "
            + user.get().getLastName()
            + "\n use the following link to reset your account: \n"
            + url);
  }
}
