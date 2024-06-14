package roomescape.application.api.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.application.service.command.CreateReservationTimeCommand;

import java.time.LocalTime;

public class CreateReservationTimeRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private final LocalTime startAt;

    @JsonCreator
    public CreateReservationTimeRequest(LocalTime startAt) {
        this.startAt = startAt;
    }

    public CreateReservationTimeCommand toCreateReservationTimeCommand() {
        return new CreateReservationTimeCommand(this.startAt);
    }

    public LocalTime getStartAt() {
        return startAt;
    }
}
