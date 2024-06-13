package roomescape.application.service.command;

public class DeleteReservationTimeCommand {

    private final Long reservationTimeId;

    public DeleteReservationTimeCommand(Long reservationTimeId) {
        this.reservationTimeId = reservationTimeId;
    }

    public Long getReservationTimeId() {
        return reservationTimeId;
    }
}
