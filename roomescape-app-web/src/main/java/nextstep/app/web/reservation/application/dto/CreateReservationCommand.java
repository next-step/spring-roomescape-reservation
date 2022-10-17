package nextstep.app.web.reservation.application.dto;

import java.time.LocalDateTime;

public record CreateReservationCommand(Long scheduleId,
                                       LocalDateTime reservationDateTime,
                                       String name) {
}
