package roomescape.api.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.api.reservation.application.request.ReserveRequest;
import roomescape.domain.common.ClockHolder;
import roomescape.domain.reservation.*;
import roomescape.domain.reservation.exception.DuplicatedReservationException;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimeId;
import roomescape.domain.reservationtime.ReservationTimeRepository;
import roomescape.domain.reservationtime.exception.ReservationTimeOutOfRangeException;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeId;
import roomescape.domain.theme.ThemeRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationCommandService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository timeRepository;
    private final ThemeRepository themeRepository;

    private final ClockHolder clockHolder;

    public ReservationId reserve(final ReserveRequest request) {
        verifyReserveAvailable(request);

        final ReservationTime time = timeRepository.getById(new ReservationTimeId(request.getTimeId()));
        final Theme theme = themeRepository.getById(new ThemeId(request.getThemeId()));

        final Reservation newReservation = Reservation.defaultOf(
                new ReservationGuestName(request.getName()),
                new ReservationDate(request.getDate()),
                time,
                theme,
                clockHolder
        );

        final Reservation saved = reservationRepository.save(newReservation);
        return ReservationId.from(saved);
    }

    private void verifyReserveAvailable(final ReserveRequest request) {
        verifyDuplicatedReservationNotExist(request);
        verifyReservationTimeRange(request);
    }

    private void verifyReservationTimeRange(final ReserveRequest request) {
        final ReservationTime time = timeRepository.getById(new ReservationTimeId(request.getTimeId()));
        final LocalDateTime requestedReservationTime = LocalDateTime.of(request.getDate(), time.getStartAt());

        if (requestedReservationTime.isBefore(clockHolder.getCurrentSeoulTime())) {
            throw new ReservationTimeOutOfRangeException(
                    "requested reservationTime %s is out of range".formatted(requestedReservationTime.toString())
            );
        }
    }

    public void cancel(final ReservationId reservationId) {
        final Reservation reservation = reservationRepository.getById(reservationId.value());
        final Reservation cancelled = reservation.delete(clockHolder);
        reservationRepository.save(cancelled);
    }

    private void verifyDuplicatedReservationNotExist(final ReserveRequest request) {
        final Optional<Reservation> reservationOpt = reservationRepository.findBy(
                new ReservationGuestName(request.getName()),
                new ReservationDate(request.getDate()),
                new ReservationTimeId(request.getTimeId())
        );

        if (reservationOpt.isEmpty()) {
            return;
        }

        final Reservation reservation = reservationOpt.get();
        if (!reservation.canceled()) {
            throw DuplicatedReservationException.fromId(ReservationId.from(reservation));
        }
    }
}
