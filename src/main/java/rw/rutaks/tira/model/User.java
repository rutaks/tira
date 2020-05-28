package rw.rutaks.tira.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import rw.rutaks.tira.dto.auth.RegisterRequestDTO;
import rw.rutaks.tira.enums.EGender;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private String firstName;

  private String lastName;

  private String email;

  @Enumerated(EnumType.STRING)
  private EGender gender;

  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

  private String uuid = UUID.randomUUID().toString();

  public User() {}

  public User(String firstName, String lastName, String gender, Date dateOfBirth) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.gender = EGender.valueOf(gender.toUpperCase());
  }

  public User(String firstName, String lastName, String gender, Date dateOfBirth, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.gender = EGender.valueOf(gender.toUpperCase());
    this.email = email;
  }

  public User(RegisterRequestDTO registerRequestDTO) {
    this.firstName = registerRequestDTO.getFirstName();
    this.lastName = registerRequestDTO.getLastName();
  }
}
