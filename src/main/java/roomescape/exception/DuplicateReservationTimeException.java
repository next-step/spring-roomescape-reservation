package roomescape.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateReservationTimeException extends RuntimeException{
    public DuplicateReservationTimeException(String message) {
        super(message);
    }
}
