package roomescape.common.exception;

import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import roomescape.reservation.exception.PastDateReservationException;
import roomescape.reservation.exception.ReservationAlreadyExistsException;
import roomescape.theme.exception.ThemeNotFoundException;
import roomescape.time.exception.CannotDeleteReserveTimeException;
import roomescape.time.exception.ReservationTimeAlreadyExistsException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({CannotDeleteReserveTimeException.class, ReservationAlreadyExistsException.class,
            NoSuchElementException.class, PastDateReservationException.class,
            ReservationTimeAlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> handleCannotDeleteReservedTimeException(RuntimeException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().body(ExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().body(ExceptionResponse.from(e.getMessage()));
    }

    @ExceptionHandler(ThemeNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleThemeNotFoundException(ThemeNotFoundException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.from(e.getMessage()));
    }
}
