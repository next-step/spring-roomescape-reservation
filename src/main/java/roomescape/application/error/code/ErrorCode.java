package roomescape.application.error.code;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    RUN_TIME_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, "런타임 에러"),
    INVALID_API_REQUEST_PARAMETER(HttpStatus.BAD_REQUEST, "유효하지 않은 API 요청입니다."),
    EXIST_RESERVATION_AT_THIS_TIME(HttpStatus.BAD_REQUEST, "이 시간에 존재하는 예약이 있어 삭제할 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
