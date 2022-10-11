package nextstep.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    public Long reserve(Reservation reservation) {
        if (reservationDao.existsReservation(reservation.getDate(), reservation.getTime())) {
            throw new AlreadyReservedException();
        }
        return reservationDao.insert(reservation);
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
