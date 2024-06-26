package roomescape.application.service.command;

import roomescape.domain.reservationtime.vo.ReservationTimeId;

public class DeleteReservationTimeCommand {

    private final Long reservationTimeId;

    public DeleteReservationTimeCommand(Long reservationTimeId) {
        this.reservationTimeId = reservationTimeId;
    }

    public Long getReservationTimeId() {
        return reservationTimeId;
    }

    public ReservationTimeId toReservationTimeId() {
        return new ReservationTimeId(reservationTimeId);
    }
}
