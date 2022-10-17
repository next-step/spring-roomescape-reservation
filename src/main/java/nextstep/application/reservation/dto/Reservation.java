package nextstep.application.reservation.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record Reservation(LocalDate date, String time, String name) {

}
