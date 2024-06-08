package roomescape.service;

import java.util.List;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;

public interface ReservationTimeService {

    ReservationTimeResponse createReservationTime(ReservationTimeRequest request);

    List<ReservationTimeResponse> findAllReservationTimes();

    void deleteReservationTime(Long id);
}
