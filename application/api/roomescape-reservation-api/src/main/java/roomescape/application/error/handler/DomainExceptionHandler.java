package roomescape.application.error.handler;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.application.error.dto.ErrorResponse;
import roomescape.application.error.logger.ExceptionLogger;
import roomescape.application.error.util.ResponseEntityFactory;
import roomescape.error.exception.DomainException;
import roomescape.error.exception.NotFoundDomainException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class DomainExceptionHandler {

    private final ExceptionLogger exceptionLogger = new ExceptionLogger(this.getClass());

    @ExceptionHandler(NotFoundDomainException.class)
    public ResponseEntity<ErrorResponse> handleException(NotFoundDomainException ex) {
        exceptionLogger.log(ex);

        return ResponseEntityFactory.notFound(ex);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleException(DomainException ex) {
        exceptionLogger.log(ex);

        return ResponseEntityFactory.badRequest(ex);
    }
}
