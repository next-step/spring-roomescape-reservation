package roomescape.domain.reservation.vo;

import roomescape.domain.validator.ObjectValidator;

public record ReservationName(String name) {

    public ReservationName(String name) {
        ObjectValidator.validateNotNull(name);
        this.name = name;
    }
}
