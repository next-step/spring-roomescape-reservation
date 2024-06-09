package roomescape.common.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import roomescape.reservation.exception.PastDateReservationException;
import roomescape.reservation.exception.ReservationAlreadyExistsException;
import roomescape.time.exception.CannotDeleteReserveTimeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CannotDeleteReserveTimeException.class)
    public ResponseEntity<Void> handleCannotDeleteReservedTimeException(CannotDeleteReserveTimeException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ReservationAlreadyExistsException.class)
    public ResponseEntity<Void> handleReservationAlreadyExistsException(ReservationAlreadyExistsException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> handleNoSuchElementException(NoSuchElementException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(PastDateReservationException.class)
    public ResponseEntity<Void> handlePastDateReservationException(PastDateReservationException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
