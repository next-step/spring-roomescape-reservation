package roomescape.adapter.in.web.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.exception.InvalidSaveDuplicationReservationTime;
import roomescape.exception.NotFoundReservationException;
import roomescape.exception.NotFoundReservationTimeException;
import roomescape.exception.NotFoundThemeException;
import roomescape.exception.ReservationTimeConflictException;

@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler({
    NotFoundReservationException.class, NotFoundReservationTimeException.class,
    NotFoundThemeException.class, InvalidSaveDuplicationReservationTime.class,
    ReservationTimeConflictException.class, IllegalArgumentException.class
  })
  public ResponseEntity<Void> handleException(RuntimeException e) {
    return ResponseEntity.badRequest()
                         .build();
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> handleException(Exception e) {
    return ResponseEntity.internalServerError()
                         .build();
  }
}
