package roomescape.apply.reservationtime.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.reservationtime.domain.repository.ReservationTimeRepository;
import roomescape.apply.reservationtime.ui.dto.ReservationTimeRequest;
import roomescape.apply.reservationtime.ui.dto.ReservationTimeResponse;

@Service
public class ReservationTimeSaver {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeSaver(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTimeResponse saveReservationTimeBy(ReservationTimeRequest request) {
        final ReservationTime reservationTime = ReservationTime.of(request.startAt());
        final ReservationTime saved = reservationTimeRepository.save(reservationTime);
        return ReservationTimeResponse.from(saved);
    }

}
