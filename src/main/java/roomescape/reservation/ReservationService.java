package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> reservations() {
        return reservationRepository
                .reservations()
                .stream()
                .map(Reservation::new)
                .toList();
    }

    public Reservation add(Reservation newReservation) {
        ReservationEntity entity =  reservationRepository.addReservation(newReservation.toEntity());
        return new Reservation(entity);
    }

    public void delete(long id) {
        reservationRepository.deleteReservation(id);
    }
}
