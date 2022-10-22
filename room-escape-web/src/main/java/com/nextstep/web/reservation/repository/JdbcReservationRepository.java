package com.nextstep.web.reservation.repository;

import com.nextstep.web.schedule.repository.ScheduleDao;
import com.nextstep.web.schedule.repository.entity.ScheduleEntity;
import nextsetp.domain.reservation.Reservation;
import nextsetp.domain.reservation.ReservationRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JdbcReservationRepository implements ReservationRepository {
    private final ReservationDao reservationDao;
    private final ScheduleDao scheduleDao;

    public JdbcReservationRepository(ReservationDao reservationDao, ScheduleDao scheduleDao) {
        this.reservationDao = reservationDao;
        this.scheduleDao = scheduleDao;
    }

    @Override
    public Long save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Override
    public Optional<Reservation> findByScheduleId(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Reservation> findAllBy(String date) {
        List<Long> scheduleIds = scheduleDao.findAllBy(date).stream()
                .map(scheduleEntity -> scheduleEntity.getId())
                .collect(Collectors.toList());

        return reservationDao.findAllBy(scheduleIds).stream()
                .map(entity -> new Reservation(entity.getScheduleId(), entity.getReservationTime(), entity.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        reservationDao.delete(id);
    }

}
