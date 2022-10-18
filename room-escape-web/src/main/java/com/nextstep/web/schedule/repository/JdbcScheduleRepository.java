package com.nextstep.web.schedule.repository;

import com.nextstep.web.schedule.repository.entity.ScheduleEntity;
import com.nextstep.web.theme.repository.ThemeDao;
import nextsetp.domain.schedule.Schedule;
import nextsetp.domain.schedule.ScheduleRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JdbcScheduleRepository implements ScheduleRepository {
    private final ScheduleDao scheduleDao;

    public JdbcScheduleRepository(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }

    @Override
    public Long save(Schedule schedule) {
        return scheduleDao.save(new ScheduleEntity(null, schedule.getThemeId(),
                schedule.getDate().toString(),
                schedule.getTime().toString()));
    }

    @Override
    public List<Schedule> findAllBy(Long themeId, LocalDate date) {
        return scheduleDao.findAllBy(themeId, date.toString()).stream()
                .map(scheduleEntity -> new Schedule(themeId,
                        LocalDate.parse(scheduleEntity.getDate()),
                        LocalTime.parse(scheduleEntity.getTime())))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        scheduleDao.delete(id);
    }
}
