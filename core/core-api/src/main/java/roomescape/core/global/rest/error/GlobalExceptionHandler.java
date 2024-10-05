package roomescape.core.global.rest.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.core.domain.common.exception.BusinessException;
import roomescape.core.global.rest.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleException(final Exception e) {
        log.error("Exception occurred: %s".formatted(e.getMessage()), e);
        return ApiResponse.serverError().toResponseEntity();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleRuntimeException(final RuntimeException e) {
        log.error("RuntimeException occurred: %s".formatted(e.getMessage()), e);
        return ApiResponse.serverError().toResponseEntity();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error("IllegalArgumentException occurred: %s".formatted(e.getMessage()), e);
        return ApiResponse.badRequest(ErrorDetails.from(e)).toResponseEntity();
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<ErrorDetails>> handleBusinessException(final BusinessException e) {
        log.info("BusinessException occurred: %s".formatted(e.getMessage()), e);

        final ApiResponse<ErrorDetails> errorResponse = ApiResponse.ofErrorDetails(
                HttpStatus.valueOf(e.getStatsCode()),
                ErrorDetails.from(e)
        );

        return errorResponse.toResponseEntity();
    }
}
