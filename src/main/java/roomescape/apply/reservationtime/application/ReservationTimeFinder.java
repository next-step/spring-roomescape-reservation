package roomescape.apply.reservationtime.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeRepository;
import roomescape.apply.reservationtime.ui.dto.ReservationTimeResponse;
import roomescape.apply.reservationtime.application.exception.NotFoundReservationTimeException;

import java.util.List;

@Service
public class ReservationTimeFinder {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeFinder(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationTimeResponse> findAll() {
        return reservationTimeRepository.findAll()
                .stream()
                .sorted(ReservationTime::compareByStartTime)
                .map(ReservationTimeResponse::from)
                .toList();
    }

    public ReservationTime findOneById(long timeId) {
        return reservationTimeRepository.findById(timeId).orElseThrow(NotFoundReservationTimeException::new);
    }
}
