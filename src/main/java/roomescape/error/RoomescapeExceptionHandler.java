package roomescape.error;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import roomescape.error.exception.NotExistsException;
import roomescape.error.exception.PastDateTimeException;
import roomescape.error.exception.ReferenceException;

@RestControllerAdvice
public class RoomescapeExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RoomescapeExceptionResponse> IllegalArgumentException(
        IllegalArgumentException e) {
        return new ResponseEntity<>(RoomescapeExceptionResponse.from(e.getMessage(),
                                        RoomescapeErrorMessage.ILLEGAL_INPUT_VALUE_EXCEPTION),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RoomescapeExceptionResponse> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return new ResponseEntity<>(RoomescapeExceptionResponse.from(
                                        e.getBindingResult().getFieldError().getDefaultMessage(),
                                        RoomescapeErrorMessage.ILLEGAL_INPUT_VALUE_EXCEPTION),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateRequestException.class)
    public ResponseEntity<RoomescapeExceptionResponse> handleDuplicateRequestException(
        DuplicateRequestException e) {
        return new ResponseEntity<>(RoomescapeExceptionResponse.from(e.getMessage(),
                                        RoomescapeErrorMessage.EXISTS_EXCEPTION),
                                    HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<RoomescapeExceptionResponse> hanleParseException() {
        return new ResponseEntity<>(RoomescapeExceptionResponse.of(
                                        RoomescapeErrorMessage.ILLEGAL_DATETIME_FORMAT_EXCEPTION),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PastDateTimeException.class)
    public ResponseEntity<RoomescapeExceptionResponse> handlePastDateTimeException(
        PastDateTimeException e) {
        return new ResponseEntity<>(RoomescapeExceptionResponse.of(e.getMessage()),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ReferenceException.class)
    public ResponseEntity<RoomescapeExceptionResponse> handleReservationAlreayExistsException(
        ReferenceException e) {
        return new ResponseEntity<>(RoomescapeExceptionResponse.of(e.getMessage()),
                                    HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NotExistsException.class)
    public ResponseEntity<RoomescapeExceptionResponse> handleNotExistsException(
        NotExistsException e) {
        return new ResponseEntity<>(RoomescapeExceptionResponse.of(e.getMessage()),
                                    HttpStatus.NOT_FOUND);
    }
}
