package roomescape.admin.repository;

import org.springframework.stereotype.Repository;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.entity.Reservation;

import java.util.List;

@Repository
public class ReservationRepository {
    public List<Reservation> readReservation() {
        return null;
    }

    public Long saveReservation(SaveReservationRequest saveReservationRequest) {
        return null;
    }

    public Reservation readReservationById(Long id) {
        return null;
    }

    public void deleteReservation(Long id) {

    }
}
