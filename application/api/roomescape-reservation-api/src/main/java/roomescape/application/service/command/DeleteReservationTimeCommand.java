package roomescape.application.service.command;

import roomescape.domain.reservationtime.vo.ReservationTimeId;

public class DeleteReservationTimeCommand {

    private final Long reservationTimeId;

    public DeleteReservationTimeCommand(Long reservationTimeId) {
        this.reservationTimeId = reservationTimeId;
    }

    public ReservationTimeId getReservationTimeId() {
        return new ReservationTimeId(reservationTimeId);
    }
}
