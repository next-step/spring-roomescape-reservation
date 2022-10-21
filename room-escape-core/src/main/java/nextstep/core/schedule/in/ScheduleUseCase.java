package nextstep.core.schedule.in;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleUseCase {
    ScheduleResponse create(ScheduleCreateRequest request);

    List<ScheduleResponse> list(Long themeId, LocalDate date);
}
