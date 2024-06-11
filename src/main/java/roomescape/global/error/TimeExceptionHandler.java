package roomescape.global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.time.error.exception.TimeException;

import java.util.List;

@RestControllerAdvice
public class TimeExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> timeExceptionHandler(TimeException timeException) {
        HttpStatus httpStatus = HttpStatus.valueOf(timeException.getStatus());
        List<String> messages = List.of(timeException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> runtimeExceptionHandler(RuntimeException runtimeException) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        List<String> messages = List.of(runtimeException.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception exception) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        List<String> messages = List.of(exception.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, messages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
