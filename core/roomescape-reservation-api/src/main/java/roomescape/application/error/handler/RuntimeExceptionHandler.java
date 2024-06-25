package roomescape.application.error.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.application.error.dto.ErrorResponse;
import roomescape.application.error.logger.ExceptionLogger;
import roomescape.application.error.util.ResponseEntityFactory;

@RestControllerAdvice
public class RuntimeExceptionHandler {

    private final ExceptionLogger exceptionLogger = new ExceptionLogger(this.getClass());

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException ex) {
        exceptionLogger.log(ex);

        return ResponseEntityFactory.internalServerError(ex);
    }
}
