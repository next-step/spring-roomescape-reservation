package roomescape.domain.reservationtime.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.common.ClockHolder;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationRepository;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
import roomescape.domain.reservationtime.exception.DupliactedReservationTimeException;
import roomescape.domain.reservationtime.exception.ReservationTimeAlreadyInUse;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationTimeCommandService {

    private final ClockHolder clockHolder;
    private final ReservationTimeRepository timeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationTime append(final ReservationTimeAppendRequest request) {
        verifyUniqueStartAt(request.getStartAt());

        final ReservationTime time = ReservationTime.defaultOf(request.getStartAt(), clockHolder);
        return timeRepository.save(time);
    }

    public void delete(final ReservationTimeId timeId) {
        final ReservationTime time = timeRepository.getById(timeId);
        verifyTimeIdNotInUse(time);
        timeRepository.delete(time);
    }

    private void verifyTimeIdNotInUse(final ReservationTime time) {
        if (anyConfirmedReservationExistsBy(time)) {
            throw ReservationTimeAlreadyInUse.from(time);
        }
    }

    private boolean anyConfirmedReservationExistsBy(final ReservationTime time) {
        final List<Reservation> reservations = reservationRepository.findAllByTimeId(time.getId());
        return reservations.stream().anyMatch(Reservation::isConfirmed);
    }

    private void verifyUniqueStartAt(final LocalTime startAt) {
        final Optional<ReservationTime> timeOpt = timeRepository.findByStartAt(startAt);
        if (timeOpt.isPresent()) {
            final ReservationTime existing = timeRepository.getByStartAt(startAt);
            throw DupliactedReservationTimeException.from(existing);
        }
    }
}
