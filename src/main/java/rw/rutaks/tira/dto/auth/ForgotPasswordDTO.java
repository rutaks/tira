package rw.rutaks.tira.dto.auth;

import javax.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordDTO {
  @Email
  private String email;
}
