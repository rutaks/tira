package rw.rutaks.tira.util;

import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import rw.rutaks.tira.exception.ValidationException;

public class ErrorUtil {

  public static void checkForErrors(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String errors =
          bindingResult.getAllErrors().stream()
              .map(DefaultMessageSourceResolvable::getDefaultMessage)
              .collect(Collectors.joining("\n"));
      throw new ValidationException(errors);
    }
  }

  public static void checkForError(BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      String error = Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage();
      throw new ValidationException(error);
    }
  }
}
