package roomescape.application.error.util;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import roomescape.application.error.dto.ErrorResponse;
import roomescape.application.error.exception.ApplicationException;
import roomescape.application.error.exception.NotFoundException;
import roomescape.domain.error.exception.DomainException;

import static org.springframework.http.HttpStatus.*;
import static roomescape.application.error.code.ApplicationErrorCode.RUN_TIME_EXCEPTION;

public final class ResponseEntityFactory {

    private ResponseEntityFactory() {
        throw new UnsupportedOperationException(ResponseEntityFactory.class.getName() + "의 인스턴스는 생성되어서 안됩니다.");
    }

    public static ResponseEntity<ErrorResponse> internalServerError(RuntimeException runtimeException) {
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(RUN_TIME_EXCEPTION.name(), runtimeException.getMessage()));
    }

    public static ResponseEntity<ErrorResponse> badRequest(BindException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ErrorResponse.from(ex));
    }

    public static ResponseEntity<ErrorResponse> badRequest(ApplicationException ex) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ErrorResponse.from(ex));
    }

    public static ResponseEntity<ErrorResponse> badRequest(DomainException domainException) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ErrorResponse.from(domainException));
    }

    public static ResponseEntity<ErrorResponse> notFound(NotFoundException notFoundException) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(ErrorResponse.from(notFoundException));
    }
}
