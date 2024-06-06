package roomescape.apply.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservation.domain.repository.ReservationRepository;

import java.util.NoSuchElementException;

@Service
public class ReservationCanceler {

    private final ReservationRepository reservationRepository;

    public ReservationCanceler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void cancelReservation(long id) {
        final long reservationId = reservationRepository.checkIdExists(id).orElseThrow(NoSuchElementException::new);
        reservationRepository.deleteById(reservationId);
    }
}
