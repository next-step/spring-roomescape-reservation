package roomescape.application.api.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import roomescape.application.service.command.CreateReservationCommand;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class CreateReservationRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private final LocalDate date;

    private final String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private final LocalTime time;

    public CreateReservationRequest(LocalDate date, String name, LocalTime time) {
        this.date = date;
        this.name = name;
        this.time = time;
    }

    public CreateReservationCommand toCreateReservationCommand() {
        return new CreateReservationCommand(name, LocalDateTime.of(date, time));
    }
}
