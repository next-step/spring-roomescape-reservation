package roomescape.support.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.apply.reservation.application.excpetion.NotFoundReservationException;
import roomescape.apply.reservationtime.application.exception.NotFoundReservationTimeException;
import roomescape.apply.theme.application.exception.NotFoundThemeException;

import java.util.NoSuchElementException;

@RestControllerAdvice(basePackages = "roomescape.apply")
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<ExceptionResponse> illegalArgumentException(IllegalArgumentException e) {
        logger.error("IllegalArgumentException caught: ", e);
        return ResponseEntity.badRequest().body(new ExceptionResponse(400, "Bad Request"));
    }

    @ExceptionHandler({NotFoundThemeException.class, NotFoundReservationTimeException.class,
                        NotFoundReservationException.class, NotFoundThemeException.class})
    public ResponseEntity<Object> noSuchElementException(NoSuchElementException e) {
        logger.error("NoSuchElementException caught: ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(404, "Not Found"));
    }

    @ExceptionHandler
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException e) {
        logger.error("RuntimeException caught: ", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(500, "Server Error"));
    }

    public record ExceptionResponse(
            int status,
            String message

    ) {

    }
}
