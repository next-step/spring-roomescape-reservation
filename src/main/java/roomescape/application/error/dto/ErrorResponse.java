package roomescape.application.error.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import roomescape.application.error.code.ErrorCode;

import java.util.Collections;
import java.util.List;

public class ErrorResponse {

    private final String code;

    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ApiValidationError> validationErrors;

    public ErrorResponse(String code, String message, List<ApiValidationError> validationErrors) {
        this.code = code;
        this.message = message;
        this.validationErrors = validationErrors;
    }

    public ErrorResponse(ErrorCode code, String message) {
        this.code = code.name();
        this.message = message;
        this.validationErrors = Collections.emptyList();
    }

    public static class ApiValidationError {

        private final String field;

        private final String message;

        public ApiValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
