package roomescape.error.code;

public enum DomainErrorCode {

    CANNOT_CREATE_RESERVATION_FOR_PAST_TIME("지나간 시간에는 예약을 할 수 없습니다.");
    //Reservations cannot be made for past times.
    private final String message;

    DomainErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
