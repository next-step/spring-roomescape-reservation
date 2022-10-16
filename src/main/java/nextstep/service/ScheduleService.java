package nextstep.service;

import nextstep.domain.ReservationRepository;
import nextstep.domain.Schedule;
import nextstep.domain.ScheduleRepository;
import nextstep.dto.schedule.ScheduleCreateRequest;
import nextstep.dto.schedule.ScheduleFindAllResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ScheduleService {
    public static final String DUPLICATE_SCHEDULE_MESSAGE = "해당 테마에 이미 존재하는 스케줄입니다.";
    public static final String CANT_DELETE_SCHEDULE = "예약이 존재하는 스케줄은 삭제할 수 없습니다.";
    private final ScheduleRepository schedules;
    private final ReservationRepository reservations;

    public ScheduleService(ScheduleRepository schedules, ReservationRepository reservations) {
        this.schedules = schedules;
        this.reservations = reservations;
    }

    public Long createSchedule(ScheduleCreateRequest scheduleCreateRequest) {
        Long themeId = scheduleCreateRequest.getThemeId();
        LocalDate date = parseDate(scheduleCreateRequest.getDate());
        LocalTime time = parseTime(scheduleCreateRequest.getTime());

        checkScheduleAvailable(themeId, date, time);
        Schedule schedule = schedules.save(new Schedule(themeId, date, time));
        return schedule.getId();
    }

    private void checkScheduleAvailable(Long themeId, LocalDate date, LocalTime time) {
        if (schedules.existsByThemeIdAndDateAndTime(themeId, date, time)) {
            throw new IllegalArgumentException(DUPLICATE_SCHEDULE_MESSAGE);
        }
    }

    public ScheduleFindAllResponse findAllSchedules(Long themeId, String inputDate) {
        LocalDate date = parseDate(inputDate);

        List<Schedule> findSchedules = schedules.findAllByThemeIdAndDate(themeId, date);
        return ScheduleFindAllResponse.from(findSchedules);
    }

    public void deleteSchedule(Long id) {
        checkDeleteAvailable(id);
        schedules.deleteById(id);
    }

    private void checkDeleteAvailable(Long id) {
        if (reservations.existsByScheduleId(id)) {
            throw new IllegalArgumentException(CANT_DELETE_SCHEDULE);
        }
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    private LocalTime parseTime(String time) {
        return LocalTime.parse(time + ":00");
    }
}
