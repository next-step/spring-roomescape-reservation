package roomescape.application.service.command;

import java.time.LocalTime;

public class CreateReservationTimeCommand {

    private final LocalTime startAt;

    public CreateReservationTimeCommand(LocalTime startAt) {
        this.startAt = startAt;
    }
}
