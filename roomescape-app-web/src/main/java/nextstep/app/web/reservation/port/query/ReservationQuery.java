package nextstep.app.web.reservation.port.query;

import nextstep.domain.reservation.domain.model.Reservation;
import nextstep.domain.reservation.domain.model.ReservationRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationQuery {
    private final ReservationRepository reservationRepository;

    public ReservationQuery(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> findAllByDate(String date) {
        return reservationRepository.findAllByDate(LocalDate.parse(date));
    }
}
