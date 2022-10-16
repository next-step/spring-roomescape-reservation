package nextstep.schedule;

import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleJdbcRepository scheduleJdbcRepository;

    public ScheduleService(ScheduleJdbcRepository scheduleJdbcRepository) {
        this.scheduleJdbcRepository = scheduleJdbcRepository;
    }

    public ScheduleResponse createSchedule(ScheduleCreateRequest request) {
        Schedule schedule = scheduleJdbcRepository.save(request.toObject());
        return ScheduleResponse.from(schedule);
    }
}
