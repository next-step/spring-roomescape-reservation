package nextstep.reservation.web;

import nextstep.reservation.domain.exception.DuplicatedReservationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(DuplicatedReservationException.class)
    ResponseEntity<String> handlerDuplicatedReservationException(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ex.getMessage());
    }
}
