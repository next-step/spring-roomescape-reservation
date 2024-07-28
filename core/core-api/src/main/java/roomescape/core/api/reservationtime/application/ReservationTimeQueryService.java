package roomescape.core.api.reservationtime.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeQueryService {

    private final ReservationTimeRepository timeRepository;

    public List<ReservationTime> fetchAll() {
        return timeRepository.findAll();
    }
}
