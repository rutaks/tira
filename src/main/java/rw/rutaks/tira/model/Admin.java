package rw.rutaks.tira.model;

import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@DiscriminatorValue("ADMIN")
@EqualsAndHashCode(callSuper = false)
public class Admin extends Person {

  public Admin(String firstName, String lastName, String gender, Date dateOfBirth, String email) {
    super(firstName, lastName, gender, dateOfBirth, email);
  }
}
