package roomescape.core.domain.reservationtime;

import roomescape.core.domain.common.exception.Assert;

public record ReservationTimeId(
        Long value
) {

    public ReservationTimeId {
        Assert.notNullField(value, "value of reservation time id");
    }
}
