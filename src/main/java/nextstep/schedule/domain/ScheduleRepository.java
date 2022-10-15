package nextstep.schedule.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ScheduleRepository {

    Schedule save(Schedule schedule);

    Schedule findById(Long id);

    List<Schedule> findAllByThemeIdAndDate(Long themeId, LocalDate date);

    int deleteById(Long id);

    boolean existsByThemeIdAndDateAndTime(Long themeId, LocalDate date, LocalTime time);

    Schedule findByDateAndTime(LocalDate date, LocalTime time);
}
