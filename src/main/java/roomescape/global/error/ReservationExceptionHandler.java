package roomescape.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.theme.error.exception.ThemeException;
import roomescape.time.error.exception.TimeException;

import java.util.List;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> themeExceptionHandler(ThemeException themeException) {
        HttpStatus httpStatus = HttpStatus.valueOf(themeException.getStatus());
        List<String> messages = List.of(themeException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> timeExceptionHandler(TimeException timeException) {
        HttpStatus httpStatus = HttpStatus.valueOf(timeException.getStatus());
        List<String> messages = List.of(timeException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException runtimeException) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> messages = List.of(runtimeException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        List<String> messages = List.of(exception.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
