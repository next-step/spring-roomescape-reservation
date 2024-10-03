package roomescape.core.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiErrorResponse {

    private final int code;
    private final HttpStatus status;
    private final String errorMessage;

    ApiErrorResponse(final HttpStatus status, final String errorMessage) {
        this.code = status.value();
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public static ApiErrorResponse serverError() {
        return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR");
    }
}
