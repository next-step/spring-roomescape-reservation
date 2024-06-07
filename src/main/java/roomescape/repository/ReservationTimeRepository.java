package roomescape.repository;

import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationDto;
import roomescape.dto.ReservationTimeDto;

import java.util.List;

public interface ReservationTimeRepository {

    ReservationTimeDto addTime(ReservationTimeDto dto);

    ReservationTimeDto findReservationTimeById(Long id);

    List<ReservationTime> findTimeList();

    void deleteTime(Long id);
}
