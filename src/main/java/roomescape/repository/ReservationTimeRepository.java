package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.time.ReservationTimeRequest;

import java.util.List;

public interface ReservationTimeRepository {

    List<ReservationTime> findTimes();

    Long createTime(ReservationTimeRequest request);

    ReservationTime findReservationTimeById(Long id);

    void deleteTime(Long id);
}
