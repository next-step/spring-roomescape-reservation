package nextstep.domain.reservation.domain.service;

import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReservationDomainService {
    private final ReservationRepository reservationRepository;

    public ReservationDomainService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Long create(LocalDateTime reservationDateTime, String name) {
        Reservation reservation = new Reservation(null, reservationDateTime.toLocalDate(), reservationDateTime.toLocalTime(), name);
        Reservation saved = reservationRepository.save(reservation);
        return saved.id();
    }
}
