package roomescape.service;

import java.util.List;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest request);

    List<ReservationResponse> findAllReservations();

    void deleteReservation(Long id);
}
