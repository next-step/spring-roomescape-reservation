package roomescape.apply.time.application;

import org.springframework.stereotype.Service;
import roomescape.apply.time.domain.ReservationTime;
import roomescape.apply.time.domain.repository.ReservationTimeRepository;
import roomescape.apply.time.ui.dto.ReservationTimeResponse;

import java.util.List;
import java.util.NoSuchElementException;

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
        return reservationTimeRepository.findById(timeId).orElseThrow(NoSuchElementException::new);
    }
}
