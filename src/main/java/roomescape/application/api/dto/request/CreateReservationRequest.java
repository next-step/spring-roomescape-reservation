package roomescape.application.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.application.service.command.CreateReservationCommand;

import java.time.LocalDate;

public class CreateReservationRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate date;

    private final String name;

    private final Long timeId;

    public CreateReservationRequest(LocalDate date, String name, Long timeId) {
        this.date = date;
        this.name = name;
        this.timeId = timeId;
    }

    public CreateReservationCommand toCreateReservationCommand() {
        return new CreateReservationCommand(name, date, timeId);
    }

    public LocalDate getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public Long getTimeId() {
        return timeId;
    }
}
