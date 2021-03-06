package rw.rutaks.tira.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rw.rutaks.tira.annotation.ValidPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
  @NotBlank(message = "First name must be provided")
  @Size(min=30, max=40)
  private String firstName;

  @NotBlank(message = "Last name must be provided")
  private String lastName;

  @NotBlank(message = "A username must be provided")
  private String username;

  @ValidPassword @NotBlank private String password;
}
