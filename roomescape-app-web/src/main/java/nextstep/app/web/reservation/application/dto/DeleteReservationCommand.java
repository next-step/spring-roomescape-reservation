package nextstep.app.web.reservation.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record DeleteReservationCommand(LocalDate date,
                                       LocalTime time) {
}
