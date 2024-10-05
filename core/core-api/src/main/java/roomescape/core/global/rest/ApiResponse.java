package roomescape.core.global.rest;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import roomescape.core.global.rest.error.ErrorDetails;

import static roomescape.core.global.rest.HttpResponseType.ERROR;

@Getter
public class ApiResponse<T> {

    private final int code;
    private final HttpStatus status;
    private final HttpResponseType responseType;
    private final T data;

    private ApiResponse(final HttpStatus status, final HttpResponseType responseType, final T data) {
        this.code = status.value();
        this.status = status;
        this.responseType = responseType;
        this.data = data;
    }

    public static ApiResponse<ErrorDetails> serverError() {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, ERROR, ErrorDetails.serverError());
    }

    public static ApiResponse<ErrorDetails> badRequest(final ErrorDetails errorDetails) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, ERROR, errorDetails);
    }

    public static ApiResponse<ErrorDetails> ofErrorDetails(final HttpStatus status, final ErrorDetails errorDetails) {
        return new ApiResponse<>(status, ERROR, errorDetails);
    }

    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        return ResponseEntity
                .status(this.getStatus())
                .body(this);
    }
}
