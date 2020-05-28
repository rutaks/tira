package rw.rutaks.tira.exception;

import javax.naming.AuthenticationException;

public class CustomAuthenticationException extends AuthenticationException {
  public CustomAuthenticationException(String msg) {
    super(msg);
  }
}
