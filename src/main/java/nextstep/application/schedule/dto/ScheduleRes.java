package nextstep.application.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import nextstep.application.themes.dto.ThemeRes;

@Builder
@Jacksonized
public record ScheduleRes(Long id, ThemeRes theme, LocalDate date,
                          @JsonFormat(shape = Shape.STRING, pattern = "hh-mm-ss")
                          LocalTime time
) {

}
