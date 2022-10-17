package nextstep.app.web.reservation.port.web;

import nextstep.app.web.reservation.application.dto.CreateReservationCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ReservationCreateRequest(Long scheduleId,
                                       String date,
                                       String time,
                                       String name) {
    public CreateReservationCommand toCommand() {
        return new CreateReservationCommand(
                scheduleId,
                LocalDateTime.parse(date + " " + time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                name
        );
    }
}
