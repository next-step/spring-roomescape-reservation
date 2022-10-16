package nextstep.web.endpoint;

import lombok.extern.slf4j.Slf4j;
import nextstep.domain.reservation.exception.ExistReservationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionAdvice {
    @ExceptionHandler(ExistReservationException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(ExistReservationException exception) {
        log.warn("[ExistReservationException] errorMessage: {}", exception.getMessage());

        return ResponseEntity.badRequest().body(new ErrorResponse(exception.getMessage()));
    }
}
