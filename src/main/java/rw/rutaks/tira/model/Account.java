package rw.rutaks.tira.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "accounts")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  private double balance = 0.00;
  private String currency = "RWF";
  @OneToOne private User user;

  public Account(User accountOwner) {
    this.user = accountOwner;
  }
}
