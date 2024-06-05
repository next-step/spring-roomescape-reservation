package roomescape.service;

import java.util.List;
import roomescape.domain.Reservation;

public interface ReservationService {

    Reservation create(Reservation reservation);

    List<Reservation> read();

    void delete(Long id);
}
