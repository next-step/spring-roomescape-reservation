package nextstep.exception;

public class ThemeIllegalArgumentException extends DomainIllegalArgumentException {

  public ThemeIllegalArgumentException(String message) {
    super(message);
  }

  public ThemeIllegalArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

  public ThemeIllegalArgumentException(Throwable cause) {
    super(cause);
  }
}
