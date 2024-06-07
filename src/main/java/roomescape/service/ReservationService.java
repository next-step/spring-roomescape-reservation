package roomescape.service;

import java.util.List;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public interface ReservationService {

    ReservationResponse createReservation(ReservationRequest request);

    List<ReservationResponse> findAllReservations();

    void deleteReservation(Long id);
}
