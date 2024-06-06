package roomescape.apply.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservation.ui.dto.ReservationRequest;
import roomescape.apply.reservation.ui.dto.ReservationResponse;
import roomescape.apply.reservationtime.application.ReservationTimeFinder;
import roomescape.apply.reservationtime.domain.ReservationTime;

@Service
public class ReservationRecorder {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeFinder reservationTimeFinder;

    public ReservationRecorder(ReservationRepository reservationRepository, ReservationTimeFinder reservationTimeFinder) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeFinder = reservationTimeFinder;
    }

    public ReservationResponse recordReservationBy(ReservationRequest request) {
        final ReservationTime time = reservationTimeFinder.findOneById(request.timeId());
        final Reservation reservation = Reservation.of(request.name(), request.date(), time);
        final Reservation saved = reservationRepository.save(reservation);
        return ReservationResponse.from(saved, time);
    }
}
