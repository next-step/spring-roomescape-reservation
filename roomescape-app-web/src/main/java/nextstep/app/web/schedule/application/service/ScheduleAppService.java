package nextstep.app.web.schedule.application.service;

import nextstep.app.web.schedule.application.dto.CreateScheduleCommand;
import nextstep.app.web.schedule.application.dto.CreateScheduleResult;
import nextstep.app.web.schedule.application.usecase.CreateScheduleUseCase;
import nextstep.app.web.schedule.application.usecase.DeleteScheduleUseCase;
import nextstep.domain.schedule.domain.service.ScheduleDomainService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleAppService implements CreateScheduleUseCase, DeleteScheduleUseCase {
    private final ScheduleDomainService scheduleDomainService;

    public ScheduleAppService(ScheduleDomainService scheduleDomainService) {
        this.scheduleDomainService = scheduleDomainService;
    }

    @Override
    public CreateScheduleResult create(CreateScheduleCommand command) {
        Long id = scheduleDomainService.create(command.themeId(), command.dateTime());
        return new CreateScheduleResult(id);
    }

    @Override
    public void deleteById(Long id) {
        scheduleDomainService.deleteById(id);
    }
}
