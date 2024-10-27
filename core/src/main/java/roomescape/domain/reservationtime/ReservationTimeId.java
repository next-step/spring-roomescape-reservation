package roomescape.domain.reservationtime;

import roomescape.domain.common.exception.Assert;

public record ReservationTimeId(
        Long value
) {

    public ReservationTimeId {
        Assert.notNullField(value, "value of reservation time id");
    }
}
