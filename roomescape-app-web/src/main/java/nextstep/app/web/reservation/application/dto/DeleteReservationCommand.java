package nextstep.app.web.reservation.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record DeleteReservationCommand(Long scheduleId,
                                       LocalDate date,
                                       LocalTime time) {
}
