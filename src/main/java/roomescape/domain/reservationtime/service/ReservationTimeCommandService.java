package roomescape.domain.reservationtime.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import roomescape.domain.reservation.model.Reservation;
import roomescape.domain.reservation.repository.ReservationRepository;
import roomescape.domain.reservationtime.exception.DupliactedReservationTimeException;
import roomescape.domain.reservationtime.exception.ReservationTimeAlreadyInUse;
import roomescape.domain.reservationtime.model.ReservationTime;
import roomescape.domain.reservationtime.model.ReservationTimeId;
import roomescape.domain.reservationtime.repository.ReservationTimeRepository;
import roomescape.global.infrastructure.ClockHolder;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        verifyTimeIdNotInUse(timeId);
        timeRepository.delete(timeId);
    }

    private void verifyTimeIdNotInUse(final ReservationTimeId timeId) {
        final List<Reservation> activeReservations = findActiveReservationsBy(timeId);

        if (!CollectionUtils.isEmpty(activeReservations)) {
            throw new ReservationTimeAlreadyInUse(
                    "Cannot delete ReservationTime(id=%d). It's already in use by Reservation(id=%s)"
                            .formatted(
                                    timeId.getValue(),
                                    activeReservations.stream()
                                            .map(reservation -> String.valueOf(reservation.getId()))
                                            .collect(Collectors.joining(","))
                            )
            );
        }
    }

    private List<Reservation> findActiveReservationsBy(final ReservationTimeId timeId) {
        return reservationRepository.findAllByTimeId(timeId)
                .stream()
                .filter(Reservation::isActive)
                .toList();
    }

    private void verifyUniqueStartAt(final LocalTime startAt) {
        final Optional<ReservationTime> timeOpt = timeRepository.findByStartAt(startAt);
        if (timeOpt.isPresent()) {
            final ReservationTime existing = timeRepository.getByStartAt(startAt);
            throw DupliactedReservationTimeException.from(existing);
        }
    }
}
