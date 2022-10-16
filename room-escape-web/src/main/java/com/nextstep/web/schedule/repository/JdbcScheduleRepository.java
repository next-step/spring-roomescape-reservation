package com.nextstep.web.schedule.repository;

import com.nextstep.web.schedule.repository.entity.ScheduleEntity;
import com.nextstep.web.theme.exception.ThemeNotFoundException;
import com.nextstep.web.theme.repository.ThemeDao;
import nextsetp.domain.schedule.Schedule;
import nextsetp.domain.schedule.ScheduleRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcScheduleRepository<T> implements ScheduleRepository<T> {
    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public JdbcScheduleRepository(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    @Override
    public Long save(T schedule) {
        if (schedule instanceof ScheduleEntity) {
            return scheduleDao.save((ScheduleEntity) schedule);
        }

        throw new RuntimeException();
    }

    @Override
    public List<Schedule> findAllBy(Long themeId, LocalDate date) {
        return scheduleDao.findAllBy(themeId, date.toString()).stream()
                .map(scheduleEntity -> new Schedule(themeDao.findById(scheduleEntity.getThemeId())
                        .orElseThrow(ThemeNotFoundException::new).toTheme(),
                        LocalDate.parse(scheduleEntity.getDate()),
                        LocalTime.parse(scheduleEntity.getTime())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        scheduleDao.delete(id);
    }
}
