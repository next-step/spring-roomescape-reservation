package roomescape.admin.service;

import org.springframework.stereotype.Service;
import roomescape.admin.dto.ReadReservationResponse;
import roomescape.admin.dto.SaveReservationRequest;
import roomescape.admin.entity.Reservation;
import roomescape.admin.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository){
        this.reservationRepository = reservationRepository;
    }

    public List<ReadReservationResponse> readReservation() {
        List<Reservation> reservation = this.reservationRepository.readReservation();

        return ReadReservationResponse.from(reservation);
    }

    public ReadReservationResponse saveReservation(SaveReservationRequest saveReservationRequest) {
        Long id = this.reservationRepository.saveReservation(saveReservationRequest);
        Reservation reservation = this.reservationRepository.readReservationById(id);

        return ReadReservationResponse.from(reservation);
    }

    public void deleteReservation(Long id) {
        this.reservationRepository.deleteReservation(id);
    }
}