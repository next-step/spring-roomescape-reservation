package nextstep.app.web.schedule.application.usecase;

import nextstep.app.web.schedule.application.dto.CreateScheduleCommand;
import nextstep.app.web.schedule.application.dto.CreateScheduleResult;

public interface CreateScheduleUseCase {
    CreateScheduleResult create(CreateScheduleCommand command);
}
