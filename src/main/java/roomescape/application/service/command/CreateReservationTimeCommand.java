package roomescape.application.service.command;

import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.reservationtime.vo.ReservationTimeStartAt;

import java.time.LocalTime;

public class CreateReservationTimeCommand {

    private final LocalTime startAt;

    public CreateReservationTimeCommand(LocalTime startAt) {
        this.startAt = startAt;
    }

    public ReservationTime toReservationTime() {
        return new ReservationTime(ReservationTimeId.empty(), new ReservationTimeStartAt(this.startAt));
    }
}
