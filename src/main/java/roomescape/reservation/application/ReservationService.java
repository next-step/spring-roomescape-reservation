package roomescape.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;

import roomescape.reservation.domain.repository.ReservationRepository;
import roomescape.reservation.domain.Reservation;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }
}
