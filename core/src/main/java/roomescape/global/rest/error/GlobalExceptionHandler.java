package roomescape.global.rest.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import roomescape.domain.common.exception.BusinessException;
import roomescape.global.rest.ApiResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ApiResponse<ErrorDetails> handleException(final Exception e) {
        log.error("Exception occurred: %s".formatted(e.getMessage()), e);
        return ApiResponse.serverError();
    }

    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<ErrorDetails> handleRuntimeException(final RuntimeException e) {
        log.error("RuntimeException occurred: %s".formatted(e.getMessage()), e);
        return ApiResponse.serverError();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<ErrorDetails> handleIllegalArgumentException(final IllegalArgumentException e) {
        log.error("IllegalArgumentException occurred: %s".formatted(e.getMessage()), e);
        return ApiResponse.badRequest(ErrorDetails.from(e));
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<ErrorDetails> handleBusinessException(final BusinessException e) {
        log.info("BusinessException occurred: %s".formatted(e.getMessage()), e);
        return ApiResponse.ofErrorDetails(HttpStatus.valueOf(e.getStatusCode()), ErrorDetails.from(e));
    }
}
