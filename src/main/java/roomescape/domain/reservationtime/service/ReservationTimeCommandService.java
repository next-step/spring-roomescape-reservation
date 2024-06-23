package roomescape.domain.reservationtime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservationtime.exception.DupliactedReservationTimeException;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationTimeCommandService {

    private final ClockHolder clockHolder;
    private final ReservationTimeRepository timeRepository;

    public ReservationTime append(final ReservationTimeAppendRequest request) {
        verifyUniqueStartAt(request.getStartAt());

        final ReservationTime time = ReservationTime.defaultOf(request.getStartAt(), clockHolder);
        return timeRepository.save(time);
    }

    private void verifyUniqueStartAt(final LocalTime startAt) {
        final Optional<ReservationTime> timeOpt = timeRepository.findByStartAt(startAt);
        if (timeOpt.isPresent()) {
            final ReservationTime existing = timeRepository.getByStartAt(startAt);
            throw DupliactedReservationTimeException.from(existing);
        }
    }
}
