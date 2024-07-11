package roomescape.domain.reservation.domain;

public enum ReservationStatus {

    CONFIRMED,
    CANCELED
    ;

    public boolean isConfirmed() {
        return this == CONFIRMED;
    }

    public boolean isCanceled() {
        return this == CANCELED;
    }
}
