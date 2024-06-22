package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public int addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> lookUpReservation() {
        return reservationRepository.readAll();
    }

    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }
}
