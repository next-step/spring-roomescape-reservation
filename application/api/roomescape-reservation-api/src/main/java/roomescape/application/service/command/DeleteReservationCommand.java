package roomescape.application.service.command;

public class DeleteReservationCommand {

    private final Long reservationId;

    public DeleteReservationCommand(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getReservationId() {
        return this.reservationId;
    }
}
