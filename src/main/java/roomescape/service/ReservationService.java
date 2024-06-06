package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

public interface ReservationService {

    ReservationResponse create(ReservationRequest request);

    List<Reservation> read();

    void delete(Long id);
}
