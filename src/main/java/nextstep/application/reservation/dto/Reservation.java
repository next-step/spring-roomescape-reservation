package nextstep.application.reservation.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record Reservation(Long scheduleId, LocalDate date, LocalTime time, String name) {

}
