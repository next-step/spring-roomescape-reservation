package nextstep.reservation.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.reservation.Reservation;
import nextstep.reservation.dao.ReservationDao;
import nextstep.reservation.exception.AlreadyReservedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Transactional
    public Long reserve(LocalDate date, LocalTime time, String name) {
        if (reservationDao.existsReservation(date, time)) {
            throw new AlreadyReservedException();
        }
        return reservationDao.insert(date, time, name);
    }

    @Transactional(readOnly = true)
    public List<Reservation> getReservationList(LocalDate date) {
        return reservationDao.findReservations(date);
    }

    @Transactional
    public void removeReservation(LocalDate date, LocalTime time) {
        reservationDao.removeReservation(date, time);
    }
}
