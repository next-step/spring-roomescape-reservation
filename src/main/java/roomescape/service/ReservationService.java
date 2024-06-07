package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationDao;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationDao reservationDao;

    public ReservationService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public Reservation createReservation(ReservationRequest reservationRequest) {
        if (reservationRequest.date().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("과거 시간은 예약할 수 없습니다.");
        }

        if (reservationDao.isDuplicate(reservationRequest)) {
            throw new IllegalArgumentException("이미 예약된 시간입니다.");
        }
        Long id = reservationDao.create(reservationRequest);
        return reservationDao.read(id);
    }

    public List<Reservation> readAllReservation() {
        return reservationDao.readAll();
    }

    public void deleteReservation(long reservationId) {
        reservationDao.delete(reservationId);
    }
}
