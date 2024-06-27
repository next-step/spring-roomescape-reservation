package roomescape.api.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import roomescape.domain.reservation.ReservationIsExists;
import roomescape.domain.reservation.ReservationNotFound;
import roomescape.domain.reservation.ReservationScheduleNotValid;
import roomescape.domain.theme.ThemeIsUsed;
import roomescape.domain.theme.ThemeNotFound;
import roomescape.domain.time.ReservationTimeIsUsed;
import roomescape.domain.time.ReservationTimeNotFound;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpClientErrorException.class)
  public ResponseEntity<String> catchClientError(HttpClientErrorException e) {
    return new ResponseEntity<>(e.getMessage(), e.getStatusCode());
  }

  @ExceptionHandler(ReservationScheduleNotValid.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String checkValidError(RuntimeException e) {
    return e.getMessage();
  }

  @ExceptionHandler({ReservationNotFound.class, ThemeNotFound.class, ReservationTimeNotFound.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String checkNotFoundError(RuntimeException e) {
    return e.getMessage();
  }

  @ExceptionHandler({ReservationIsExists.class, ReservationTimeIsUsed.class, ThemeIsUsed.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public String checkExistsError(ReservationIsExists e) {
    return e.getMessage();
  }
}
