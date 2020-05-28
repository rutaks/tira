package rw.rutaks.tira.model;

import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.rutaks.tira.dto.auth.RegisterRequestDTO;

@Data
@Entity
@DiscriminatorValue("USER")
@EqualsAndHashCode(callSuper = false)
public class User extends Person {

  public User() {}

  public User(String firstName, String lastName, String gender, Date dateOfBirth) {
    super(firstName, lastName, gender, dateOfBirth);
  }

  public User(String firstName, String lastName, String gender, Date dateOfBirth, String email) {
    super(firstName, lastName, gender, dateOfBirth, email);
  }

  public User(RegisterRequestDTO registerRequestDTO) {
    super(registerRequestDTO);
  }
}
