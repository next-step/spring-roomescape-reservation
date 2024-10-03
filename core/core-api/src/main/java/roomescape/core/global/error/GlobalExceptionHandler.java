package roomescape.core.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(final Exception e) {
        log.error("Exception occurred: %s".formatted(e.getMessage()), e);
        return toErrorResponseEntity(ApiErrorResponse.serverError());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleRuntimeException(final RuntimeException e) {
        log.error("RuntimeException occurred: %s".formatted(e.getMessage()), e);
        return toErrorResponseEntity(ApiErrorResponse.serverError());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error("IllegalArgumentException occurred: %s".formatted(e.getMessage()), e);
        return toErrorResponseEntity(new ApiErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage()));
    }

    private ResponseEntity<ApiErrorResponse> toErrorResponseEntity(final ApiErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
