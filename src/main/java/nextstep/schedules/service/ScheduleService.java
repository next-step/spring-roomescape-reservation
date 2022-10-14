package nextstep.schedules.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.schedules.Schedule;
import nextstep.schedules.controller.response.FindScheduleResponse;
import nextstep.schedules.dao.ScheduleDao;
import nextstep.schedules.exception.ThemesNotFoundException;
import nextstep.themes.dao.ThemesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleService {

    private final ThemesDao themesDao;
    private final ScheduleDao scheduleDao;

    public ScheduleService(ThemesDao themesDao, ScheduleDao scheduleDao) {
        this.themesDao = themesDao;
        this.scheduleDao = scheduleDao;
    }

    @Transactional
    public Long createSchedule(Long themeId, LocalDate date, LocalTime time) {
        themesDao.findById(themeId).orElseThrow(ThemesNotFoundException::new);
        return scheduleDao.insert(themeId, date, time);
    }

    @Transactional(readOnly = true)
    public List<Schedule> findSchedule(Long themeId, LocalDate date) {
        return scheduleDao.findWithThemesByThemeIdAndDate(themeId, date);
    }
}
