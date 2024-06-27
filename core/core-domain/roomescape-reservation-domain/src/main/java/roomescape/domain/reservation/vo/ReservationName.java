package roomescape.domain.reservation.vo;

import roomescape.util.validator.ObjectValidator;

public record ReservationName(String name) {

    public ReservationName(String name) {
        ObjectValidator.validateNotNull(name);
        this.name = name;
    }
}
