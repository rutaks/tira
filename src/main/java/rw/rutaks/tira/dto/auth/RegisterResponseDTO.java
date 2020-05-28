package rw.rutaks.tira.dto.auth;

import lombok.Data;
import lombok.NoArgsConstructor;
import rw.rutaks.tira.model.User;

@Data
@NoArgsConstructor
public class RegisterResponseDTO {
  private String jwt;
  private User user;

  public RegisterResponseDTO(String jwt, User user) {
    this.jwt = jwt;
    this.user = user;
  }
}
