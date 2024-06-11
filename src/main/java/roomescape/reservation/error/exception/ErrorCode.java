package roomescape.reservation.error.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_THEME_NAME_FORMAT_ERROR(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 형식의 이름이 입력되었습니다. 2-20자 이내의 영문, 한글, 띄어쓰기만 가능합니다."),
    INVALID_THEME_DATE_FORMAT_ERROR(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 형식의 날짜가 입력되었습니다. 년, 월, 일을 정확히 입력해주세요.");

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
