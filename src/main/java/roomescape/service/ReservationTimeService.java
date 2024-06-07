package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;

import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationTimeService {
    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeService(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    public ReservationTime createTime(ReservationTimeRequest reservationTimeRequest) {
        long timeId = reservationTimeDao.create(reservationTimeRequest);
        LocalTime startAt = reservationTimeRequest.startAt();
        return new ReservationTime(timeId, startAt);
    }

    public List<ReservationTime> readAllTime() {
        return reservationTimeDao.readAll();
    }

    public void deleteTime(long id) {
        reservationTimeDao.delete(id);
    }
}
