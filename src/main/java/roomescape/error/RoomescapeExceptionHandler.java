package roomescape.error;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import roomescape.error.exception.NotExistsException;
import roomescape.error.exception.PastDateTimeException;
import roomescape.error.exception.ReferenceException;

@RestControllerAdvice
public class RoomescapeExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RoomescapeExceptionResponse IllegalArgumentException(IllegalArgumentException e) {
        return RoomescapeExceptionResponse.from(e.getMessage(),
            RoomescapeErrorMessage.ILLEGAL_INPUT_VALUE_EXCEPTION);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RoomescapeExceptionResponse handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return RoomescapeExceptionResponse.from(
            e.getBindingResult().getFieldError().getDefaultMessage(),
            RoomescapeErrorMessage.ILLEGAL_INPUT_VALUE_EXCEPTION);
    }

    @ExceptionHandler(DuplicateRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RoomescapeExceptionResponse handleDuplicateRequestException(
        DuplicateRequestException e) {
        return RoomescapeExceptionResponse.from(e.getMessage(),
            RoomescapeErrorMessage.EXISTS_EXCEPTION);
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RoomescapeExceptionResponse hanleParseException() {
        return RoomescapeExceptionResponse.of(
            RoomescapeErrorMessage.ILLEGAL_DATETIME_FORMAT_EXCEPTION);
    }

    @ExceptionHandler(PastDateTimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RoomescapeExceptionResponse handlePastDateTimeException(PastDateTimeException e) {
        return RoomescapeExceptionResponse.of(e.getMessage());
    }

    @ExceptionHandler(ReferenceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RoomescapeExceptionResponse handleReservationAlreayExistsException(
        ReferenceException e) {
        return RoomescapeExceptionResponse.of(e.getMessage());
    }

    @ExceptionHandler(NotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RoomescapeExceptionResponse handleNotExistsException(NotExistsException e) {
        return RoomescapeExceptionResponse.of(e.getMessage());
    }
}
