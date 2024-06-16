package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequest;

import java.util.List;
import java.util.Optional;

public interface ReservationTimeRepository {

    List<ReservationTime> findTimes();

    Long createTime(ReservationTimeRequest request);

    ReservationTime findReservationTimeById(Long id);

    void deleteTime(Long id);

    int countReservationTimeByStartAt(ReservationTimeRequest request);
}
