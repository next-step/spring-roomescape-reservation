package roomescape.time.global.error.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_TIME_FORMAT_ERROR(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 형식의 시간이 입력되었습니다.");

    private final int status;
    private final String errorMessage;

    ErrorCode(int status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
