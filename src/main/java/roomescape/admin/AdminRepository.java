package roomescape.admin;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminRepository {
    public List<Reservation> readReservation() {
        return null;
    }

    public void saveReservation(SaveReservationRequest saveReservationRequest) {
    }

    public void deleteReservation(Long id) {

    }
}
