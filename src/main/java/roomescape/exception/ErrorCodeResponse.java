package roomescape.exception;

public class ErrorCodeResponse {

    private ErrorCode errorCode;

    private String message;

    public ErrorCodeResponse(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}
