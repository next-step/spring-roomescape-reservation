package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.repositories.ReservationRepository;
import roomescape.reservation.data.ReservationSearchResponse;

import java.util.List;

@Service
public class ReservationService {

//    private final ReservationRepository reservationRepository;

//    @Autowired
//    public ReservationService(ReservationRepository reservationRepository) {
//        this.reservationRepository = reservationRepository;
//    }

    public List<ReservationSearchResponse> searchAllReservations() {
        return null;
    }
}
