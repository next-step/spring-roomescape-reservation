package nextstep.schedule;

import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private final ScheduleJdbcRepository scheduleJdbcRepository;

    public ScheduleService(ScheduleJdbcRepository scheduleJdbcRepository) {
        this.scheduleJdbcRepository = scheduleJdbcRepository;
    }

    public ScheduleResponse createSchedule(ScheduleCreateRequest request) {
        if (scheduleJdbcRepository.existsByThemeIdAndDateAndTime(request.getThemeId(), request.getDate(), request.getTime())) {
            throw new IllegalStateException("스케줄 생성 실패: 해당 테마의 해당 일시에 이미 스케줄이 존재합니다.");
        }
        Schedule schedule = scheduleJdbcRepository.save(request.toObject());
        return ScheduleResponse.from(schedule);
    }
}
