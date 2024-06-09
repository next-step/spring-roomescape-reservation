package roomescape.support.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.apply.reservation.application.excpetion.DuplicateReservationException;
import roomescape.apply.reservation.application.excpetion.NotFoundReservationException;
import roomescape.apply.reservationtime.application.exception.NotFoundReservationTimeException;
import roomescape.apply.reservationtime.application.exception.ReservationTimeReferencedException;
import roomescape.apply.theme.application.exception.NotFoundThemeException;
import roomescape.apply.theme.application.exception.ThemeReferencedException;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "roomescape.apply")
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class,
            ReservationTimeReferencedException.class, ThemeReferencedException.class
    })
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        logger.error("IllegalArgumentException caught: " + e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(400, e.getMessage()));
    }

    @ExceptionHandler(DuplicateReservationException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateReservationException(DuplicateReservationException e) {
        logger.error("DuplicateReservationException caught: " + e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(400, e.getMessage()));
    }

    @ExceptionHandler({NotFoundThemeException.class, NotFoundReservationTimeException.class, NotFoundReservationException.class})
    public ResponseEntity<ExceptionResponse> handleNoSuchElementException(NoSuchElementException e) {
        logger.error("NoSuchElementException caught: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(404, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logger.error("HttpMessageNotReadableException caught: " + e.getMessage());
        return ResponseEntity.badRequest().body(new ExceptionResponse(400, "JSON parse error: " + e.getMostSpecificCause().getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
        logger.error("RuntimeException caught: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(500, "Server Error"));
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    public record ExceptionResponse(
            int status,
            String message

    ) {

    }
}
