package com.nextstep.web.reservation.repository;

import com.nextstep.web.reservation.repository.entity.ReservationEntity;
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
    private ReservationDao reservationDao;

    public JdbcReservationRepository(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public Long save(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Override
    public Optional<Reservation> findBy(String date, String time) {
         Optional<ReservationEntity> reservationEntity = reservationDao.findBy(date, time);
         return reservationEntity.map(entity -> new Reservation(LocalDate.parse(entity.getDate()),
                 LocalTime.parse(entity.getTime()),
                 entity.getName()));
    }

    @Override
    public List<Reservation> findAllBy(String date) {
        return reservationDao.findAllBy(date).stream()
                .map(entity -> new Reservation(LocalDate.parse(entity.getDate()),
                LocalTime.parse(entity.getTime()),
                entity.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String date, String time) {
        reservationDao.delete(date, time);
    }
}
