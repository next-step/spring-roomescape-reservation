package nextstep.app.web.reservation.application.dto;

import java.time.LocalDateTime;

public record CreateReservationCommand(LocalDateTime reservationDateTime,
                                       String name) {
}
