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
import roomescape.error.exception.ReferentialIntegrityException;

@RestControllerAdvice
public class RoomescapeExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RoomescapeExceptionResponse IllegalArgumentException(IllegalArgumentException e) {
        return RoomescapeExceptionResponse.from(RoomescapeErrorMessage.ILLEGAL_INPUT_VALUE_EXCEPTION,
            e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RoomescapeExceptionResponse handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e) {
        return RoomescapeExceptionResponse.from(RoomescapeErrorMessage.ILLEGAL_INPUT_VALUE_EXCEPTION,
            e.getBindingResult().getFieldError().getDefaultMessage());
    }

    @ExceptionHandler(DuplicateRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RoomescapeExceptionResponse handleDuplicateRequestException(
        DuplicateRequestException e) {
        return RoomescapeExceptionResponse.from(RoomescapeErrorMessage.EXISTS_EXCEPTION,
            e.getMessage());
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
        return RoomescapeExceptionResponse.of(e.getErrorCode());
    }

    @ExceptionHandler(ReferentialIntegrityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public RoomescapeExceptionResponse handleReservationAlreayExistsException(
        ReferentialIntegrityException e) {
        return RoomescapeExceptionResponse.from(e.getErrorCode(), e.getMessage());
    }

    @ExceptionHandler(NotExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RoomescapeExceptionResponse handleNotExistsException(NotExistsException e) {
        return RoomescapeExceptionResponse.from(e.getErrorCode(), e.getMessage());
    }
}
