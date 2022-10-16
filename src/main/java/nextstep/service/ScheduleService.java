package nextstep.service;

import nextstep.domain.Schedule;
import nextstep.domain.ScheduleRepository;
import nextstep.dto.ScheduleCreateRequest;
import nextstep.dto.ScheduleFindAllResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {
    private final ScheduleRepository schedules;

    public ScheduleService(ScheduleRepository schedules) {
        this.schedules = schedules;
    }

    public Long createSchedule(ScheduleCreateRequest scheduleCreateRequest) {
        Long themeId = scheduleCreateRequest.getThemeId();
        LocalDate date = parseDate(scheduleCreateRequest.getDate());
        LocalTime time = parseTime(scheduleCreateRequest.getTime());

        Schedule schedule = schedules.save(new Schedule(themeId, date, time));
        return schedule.getId();
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    private LocalTime parseTime(String time) {
        return LocalTime.parse(time + ":00");
    }

    public ScheduleFindAllResponse findAllSchedules(Long themeId, String inputDate) {
        LocalDate date = parseDate(inputDate);

        List<Schedule> findSchedules = schedules.findAllByThemeIdAndDate(themeId, date);
        return ScheduleFindAllResponse.from(findSchedules);
    }

    public void deleteSchedule(Long id) {
        // todo 예약이 있으면 스케줄 삭제 불가
        schedules.deleteById(id);
    }
}
