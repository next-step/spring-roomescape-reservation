package roomescape.domain.reservation.vo;

import roomescape.domain.validator.IdValidator;

public record ReservationId(Long id) {

    public ReservationId(Long id) {
        IdValidator.validate(id);
        this.id = id;
    }
}
