package com.nextstep.web.schedule.service;

import com.nextstep.web.schedule.dto.ScheduleResponse;
import com.nextstep.web.schedule.repository.ScheduleDao;
import com.nextstep.web.schedule.repository.entity.ScheduleEntity;
import com.nextstep.web.theme.exception.ThemeNotFoundException;
import com.nextstep.web.theme.repository.ThemeDao;
import com.nextstep.web.theme.repository.entity.ThemeEntity;
import nextsetp.domain.theme.Theme;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ScheduleQueryService {
    private final ScheduleDao scheduleDao;
    private final ThemeDao themeDao;

    public ScheduleQueryService(ScheduleDao scheduleDao, ThemeDao themeDao) {
        this.scheduleDao = scheduleDao;
        this.themeDao = themeDao;
    }

    public List<ScheduleResponse> findAllBy(Long themeId, LocalDate date) {
        ThemeEntity themeEntity = themeDao.findById(themeId).orElseThrow(ThemeNotFoundException::new);
        List<ScheduleEntity> scheduleEntities = scheduleDao.findAllBy(themeId, date.toString());
        return ScheduleResponse.toListFromEntity(scheduleEntities, themeEntity);
    }
}
