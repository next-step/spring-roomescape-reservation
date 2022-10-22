package com.nextstep.web.reservation.app;

import com.nextstep.web.reservation.dto.ReservationResponse;
import com.nextstep.web.reservation.repository.ReservationDao;
import com.nextstep.web.reservation.repository.entity.ReservationEntity;
import com.nextstep.web.schedule.repository.ScheduleDao;
import com.nextstep.web.schedule.repository.entity.ScheduleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReservationQueryService {
    private final ScheduleDao scheduleDao;
    private final ReservationDao reservationDao;

    public ReservationQueryService(ScheduleDao scheduleDao, ReservationDao reservationDao) {
        this.scheduleDao = scheduleDao;
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> findAllBy(String date) {
        List<ScheduleEntity> scheduleEntities = scheduleDao.findAllBy(date);
        List<ReservationEntity> reservations = reservationDao.findAllBy(scheduleEntities
                .stream().map(ScheduleEntity::getId)
                .collect(Collectors.toList()));
        return ReservationResponse.toListFromEntity(reservations, scheduleEntities);
    }
}
