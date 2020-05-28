package rw.rutaks.tira.dto.auth;

import lombok.Data;
import rw.rutaks.tira.annotation.ValidPassword;

@Data
public class ResetPasswordDTO {
  @ValidPassword(message = "Password is not valid")
  private String password;
  @ValidPassword(message = "Confirm Password is not valid")
  private String confirmPassword;
}
