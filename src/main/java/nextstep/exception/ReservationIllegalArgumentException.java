package nextstep.exception;

public class ReservationIllegalArgumentException extends DomainIllegalArgumentException {

  public ReservationIllegalArgumentException(String message) {
    super(message);
  }

  public ReservationIllegalArgumentException(String message, Throwable cause) {
    super(message, cause);
  }

  public ReservationIllegalArgumentException(Throwable cause) {
    super(cause);
  }
}
