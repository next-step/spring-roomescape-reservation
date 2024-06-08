package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadReservationResponse;
import roomescape.admin.dto.SaveReservationRequest;

import java.util.List;

@Service
public class ReservationService {
    public List<ReadReservationResponse> readReservation() {
        return null;
    }

    public ReadReservationResponse saveReservation(SaveReservationRequest saveReservationRequest) {
        return null;
    }

    public void deleteReservation(Long id) {
    }
}
