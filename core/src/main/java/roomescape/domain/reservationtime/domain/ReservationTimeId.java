package roomescape.domain.reservationtime.domain;

import roomescape.domain.common.exception.Assert;

public record ReservationTimeId(
        Long value
) {

    public ReservationTimeId {
        Assert.notNullField(value, "value of reservation time id");
    }
}
