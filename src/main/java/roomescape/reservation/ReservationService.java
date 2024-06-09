package roomescape.reservation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(@Autowired ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    private final AtomicInteger lastId = new AtomicInteger(0);

    public ReservationRepository reservations() {
        return reservationRepository;
    }

    public Reservation add(Reservation newReservation) {
        configReservationId(newReservation);
        reservationRepository.add(newReservation);
        return newReservation;
    }

    public void delete(long id) {
        reservationRepository.delete(id);
    }

    private void configReservationId(Reservation newReservation) {
        int id = lastId.incrementAndGet();
        newReservation.setId(id);
    }
}
