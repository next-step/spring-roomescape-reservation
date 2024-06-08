package roomescape.admin.repository;

import org.springframework.stereotype.Repository;
import roomescape.admin.dto.SaveReservationTimeRequest;
import roomescape.admin.entity.ReservationTime;

import java.util.List;

@Repository
public class ReservationTimeRepository {
    public List<ReservationTime> readReservationTime() {
        return null;
    }

    public Long saveReservationTime(SaveReservationTimeRequest saveReservationTimeRequest) {
        return null;
    }

    public ReservationTime readReservationTimeById(Long id) {
        return null;
    }

    public void deleteReservationTime(Long id) {

    }
}
