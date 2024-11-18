package roomescape.global.rest;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import roomescape.global.rest.error.ErrorDetails;


@Getter
public final class ApiResponse<T> {

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final HttpResponseType responseType;
    private final T data;

    private ApiResponse(final HttpStatus httpStatus, final HttpResponseType responseType, final T data) {
        Assert.notNull(httpStatus, "status must not be null");
        Assert.notNull(responseType, "responseType must not be null");

        this.statusCode = httpStatus.value();
        this.httpStatus = httpStatus;
        this.responseType = responseType;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(final T data) {
        return successOf(HttpStatus.OK, data);
    }

    public static <T> ApiResponse<T> okWithEmptyData() {
        return successOf(HttpStatus.OK, null);
    }

    public static ApiResponse<ErrorDetails> badRequest(final ErrorDetails errorDetails) {
        return errorOf(HttpStatus.BAD_REQUEST, errorDetails);
    }

    public static ApiResponse<ErrorDetails> notFound(final ErrorDetails errorDetails) {
        return errorOf(HttpStatus.NOT_FOUND, errorDetails);
    }

    public static ApiResponse<ErrorDetails> serverError() {
        return errorOf(HttpStatus.INTERNAL_SERVER_ERROR, ErrorDetails.serverError());
    }

    public static ApiResponse<ErrorDetails> ofErrorDetails(final HttpStatus status, final ErrorDetails errorDetails) {
        return errorOf(status, errorDetails);
    }

    private static <T> ApiResponse<T> successOf(final HttpStatus status, final T data) {
        return new ApiResponse<>(status, HttpResponseType.SUCCESS, data);
    }

    private static <T> ApiResponse<T> errorOf(final HttpStatus status, final T data) {
        return new ApiResponse<>(status, HttpResponseType.ERROR, data);
    }

    public ResponseEntity<ApiResponse<T>> toResponseEntity() {
        return ResponseEntity
                .status(this.getHttpStatus())
                .body(this);
    }
}
