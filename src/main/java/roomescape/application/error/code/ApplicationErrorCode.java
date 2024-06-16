package roomescape.application.error.code;

public enum ApplicationErrorCode {

    RUN_TIME_EXCEPTION("런타임 에러"),
    INVALID_API_REQUEST_PARAMETER("유효하지 않은 API 요청입니다."),
    EXIST_RESERVATION_AT_THIS_TIME("이 시간에 존재하는 예약이 있어 삭제할 수 없습니다."),
    NOT_FOUND_ENTITY("엔티티를 찾을 수 없습니다.");

    private final String message;

    ApplicationErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
