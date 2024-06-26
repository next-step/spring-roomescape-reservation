package roomescape.application.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import roomescape.application.error.exception.ApplicationException;
import roomescape.error.exception.DomainException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static roomescape.application.error.code.ApplicationErrorCode.INVALID_API_REQUEST_PARAMETER;

public class ErrorResponse {

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ApiValidationError> validationErrors;

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
        this.validationErrors = Collections.emptyList();
    }

    public ErrorResponse(String code, String message, List<ApiValidationError> validationErrors) {
        this.code = code;
        this.message = message;
        this.validationErrors = validationErrors;
    }

    public static ErrorResponse from(BindException ex) {
        return new ErrorResponse(INVALID_API_REQUEST_PARAMETER.name(), ex.getMessage(), ApiValidationError.toApiValidationErrors(ex));
    }

    public static ErrorResponse from(ApplicationException ex) {
        return new ErrorResponse(ex.getCodeName(), ex.getMessage());
    }

    public static ErrorResponse from(DomainException ex) {
        return new ErrorResponse(ex.getCodeName(), ex.getMessage());
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public List<ApiValidationError> getValidationErrors() {
        return validationErrors;
    }

    public static class ApiValidationError {

        private final String field;

        private final String message;

        public ApiValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        private static ApiValidationError of(FieldError fieldError) {
            return new ApiValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        public static List<ApiValidationError> toApiValidationErrors(BindException ex) {
            return ex.getBindingResult().getFieldErrors().stream()
                    .map(ApiValidationError::of)
                    .collect(Collectors.toList());
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
