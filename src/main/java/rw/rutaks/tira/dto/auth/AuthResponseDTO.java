package rw.rutaks.tira.dto.auth;

public class AuthResponseDTO {
  private String jwt;

  public AuthResponseDTO(String jwt) {
    this.jwt = jwt;
  }

  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }
}
