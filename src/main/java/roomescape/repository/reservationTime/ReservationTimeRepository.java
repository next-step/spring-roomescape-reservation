package roomescape.repository.reservationTime;

import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.List;

@Repository
public interface ReservationTimeRepository {
    public List<ReservationTime> findAll();

    public ReservationTime findByTimeId(String timeId);

    public ReservationTime createReservationTime(ReservationTime reservationTime);

    public void deleteReservationTime(Long id);
}
