package roomescape.domain.reservationtime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationTimeQueryService {

    private final ReservationTimeRepository timeRepository;

    public List<ReservationTime> fetchAll() {
        return timeRepository.findAll();
    }
}
