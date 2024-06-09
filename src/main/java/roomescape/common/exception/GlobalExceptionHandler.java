package roomescape.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import roomescape.time.exception.CannotDeleteReserveTimeException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(CannotDeleteReserveTimeException.class)
    public ResponseEntity<Void> handleCannotDeleteReservedTimeException(CannotDeleteReserveTimeException e) {
        log.warn("{}", e.getMessage());
        return ResponseEntity.badRequest().build();
    }
}
