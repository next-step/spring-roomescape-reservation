package nextstep.application.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import nextstep.application.schedule.dto.ScheduleRes;

@Builder
@Jacksonized
public record ReservationRes(Long id, ScheduleRes schedule, LocalDate date,
                             @JsonFormat(shape = Shape.STRING, pattern = "HH-mm-ss") LocalTime time,
                             String name) {

}
