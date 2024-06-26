package roomescape.error.code;

public enum DomainErrorCode {

    CANNOT_CREATE_RESERVATION_FOR_PAST_TIME("지나간 시간에는 예약을 할 수 없습니다."),
    NOT_FOUND_RESERVATION("예약을 찾을 수 없습니다."),
    NOT_FOUND_RESERVATION_TIME("예약 시간을 찾을 수 없습니다."),
    NOT_FOUND_THEME("테마를 찾을 수 없습니다.");

    private final String message;

    DomainErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
