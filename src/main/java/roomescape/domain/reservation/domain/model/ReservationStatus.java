package roomescape.domain.reservation.domain.model;

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
