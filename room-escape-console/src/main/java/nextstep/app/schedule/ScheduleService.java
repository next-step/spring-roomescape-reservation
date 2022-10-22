package nextstep.app.schedule;

import nextsetp.domain.schedule.Schedule;
import nextsetp.domain.schedule.ScheduleRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public void save(Long themeId, LocalDate date, LocalTime time) {
        scheduleRepository.save(new Schedule(themeId, date, time));
    }

    public List<Schedule> findAllBy(Long themeId, LocalDate date) {
        return scheduleRepository.findAllBy(themeId, date);
    }

    public void delete(Long reservationId) {
        scheduleRepository.delete(reservationId);
    }
}
