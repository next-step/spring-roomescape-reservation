package roomescape.domain.reservationtime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
import roomescape.global.infrastructure.ClockHolder;

@Service
@RequiredArgsConstructor
public class ReservationTimeCommandService {

    private final ClockHolder clockHolder;
    private final ReservationTimeRepository timeRepository;

    public ReservationTime append(final ReservationTimeAppendRequest request) {
        final ReservationTime time = ReservationTime.defaultOf(request.getStartAt(), clockHolder);
        return timeRepository.save(time);
    }
}
