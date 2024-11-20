package roomescape.domain.reservationtime.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeQueryService {

    private final ReservationTimeRepository timeRepository;

    public List<ReservationTime> fetchAll() {
        return timeRepository.findAll();
    }
}
