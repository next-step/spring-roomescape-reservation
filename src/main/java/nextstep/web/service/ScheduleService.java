package nextstep.web.service;

import nextstep.domain.schedule.model.Schedule;
import nextstep.domain.schedule.service.ScheduleDomainService;
import nextstep.domain.theme.model.Theme;
import nextstep.domain.theme.service.ThemeDomainService;
import nextstep.web.endpoint.schedule.request.ScheduleCreateRequest;
import nextstep.web.endpoint.schedule.request.ScheduleSearchRequest;
import nextstep.web.endpoint.schedule.response.ScheduleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleDomainService scheduleDomainService;
    private final ThemeDomainService themeDomainService;

    public ScheduleService(ScheduleDomainService scheduleDomainService, ThemeDomainService themeDomainService) {
        this.scheduleDomainService = scheduleDomainService;
        this.themeDomainService = themeDomainService;
    }

    public Long create(ScheduleCreateRequest request) {
        Schedule schedule = scheduleDomainService.create(request.getThemeId(), request.getDate(), request.getTime());

        return schedule.getId();
    }

    public List<ScheduleResponse> findAllByThemeIdAndDate(ScheduleSearchRequest request) {
        Theme theme = themeDomainService.findById(request.getThemeId());
        List<Schedule> schedules = scheduleDomainService.findAllByThemeIdAndDate(request.getThemeId(), request.getDate());

        return ScheduleResponse.fromList(schedules, theme);
    }

    public void deleteById(Long id) {
        scheduleDomainService.deleteById(id);
    }
}
