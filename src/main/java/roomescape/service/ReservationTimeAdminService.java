package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.model.ReservationTime;
import roomescape.respository.ReservationTimeDAO;

import java.util.List;

@Service
public class ReservationTimeAdminService {
    private final ReservationTimeDAO reservationTimeDao;

    public ReservationTimeAdminService(ReservationTimeDAO reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime createReservationTime(ReservationTime time) {
        return reservationTimeDao.insertReservationTime(time);
    }

    public List<ReservationTime> getReservationTime() {
        return reservationTimeDao.readReservationTime();

    }
}
