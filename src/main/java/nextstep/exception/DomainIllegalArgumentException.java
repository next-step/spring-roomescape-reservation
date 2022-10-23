package nextstep.exception;

public class DomainIllegalArgumentException extends IllegalArgumentException {

  public DomainIllegalArgumentException(String message) {
    super(message);
  }

  public DomainIllegalArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

  public DomainIllegalArgumentException(Throwable cause) {
    super(cause);
  }
}
