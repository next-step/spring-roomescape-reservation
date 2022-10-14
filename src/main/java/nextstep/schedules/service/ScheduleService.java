package nextstep.schedules.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.exception.AlreadyReservedException;
import nextstep.schedules.Schedule;
import nextstep.schedules.dao.ScheduleDao;
import nextstep.schedules.exception.ScheduleNotFoundException;
import nextstep.schedules.exception.ThemesNotFoundException;
import nextstep.themes.dao.ThemesDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleService {

    private final ThemesDao themesDao;
    private final ScheduleDao scheduleDao;
    private final ReservationDao reservationDao;

    public ScheduleService(ThemesDao themesDao, ScheduleDao scheduleDao,
                           ReservationDao reservationDao) {
        this.themesDao = themesDao;
        this.scheduleDao = scheduleDao;
        this.reservationDao = reservationDao;
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

    @Transactional
    public void removeSchedule(Long id) {
        Schedule schedule = scheduleDao.findById(id).orElseThrow(ScheduleNotFoundException::new);

        if (reservationDao.existsReservation(schedule.getDate(), schedule.getTime())) {
            throw new AlreadyReservedException();
        };

        scheduleDao.removeById(id);
    }
}
