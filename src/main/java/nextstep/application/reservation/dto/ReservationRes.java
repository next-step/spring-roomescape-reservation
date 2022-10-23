package nextstep.application.reservation.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import nextstep.application.schedule.dto.ScheduleRes;

@Builder
@Jacksonized
public record ReservationRes(Long id, ScheduleRes schedule, LocalDate date, String time, String name) {

}
