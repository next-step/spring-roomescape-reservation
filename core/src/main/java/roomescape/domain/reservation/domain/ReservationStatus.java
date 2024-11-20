package roomescape.domain.reservation.domain;

import lombok.Getter;

@Getter
public enum ReservationStatus {

    CONFIRMED("확정"),
    CANCELED("취소")
    ;

    private final String description;

    ReservationStatus(final String description) {
        this.description = description;
    }

    public boolean isCanceled() {
        return this == CANCELED;
    }

    public boolean isConfirmed() {
        return this == CONFIRMED;
    }
}
