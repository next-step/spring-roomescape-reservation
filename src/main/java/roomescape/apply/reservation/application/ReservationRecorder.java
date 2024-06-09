package roomescape.apply.reservation.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservation.application.excpetion.DuplicateReservationException;
import roomescape.apply.reservation.domain.Reservation;
import roomescape.apply.reservation.domain.repository.ReservationRepository;
import roomescape.apply.reservation.ui.dto.ReservationRequest;
import roomescape.apply.reservation.ui.dto.ReservationResponse;
import roomescape.apply.reservationtime.application.ReservationTimeFinder;
import roomescape.apply.reservationtime.domain.ReservationTime;
import roomescape.apply.theme.application.ThemeFinder;
import roomescape.apply.theme.domain.Theme;

@Service
public class ReservationRecorder {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeFinder reservationTimeFinder;
    private final ThemeFinder themeFinder;
    private final ReservationFinder reservationFinder;

    public ReservationRecorder(ReservationRepository reservationRepository, ReservationTimeFinder reservationTimeFinder,
                               ThemeFinder themeFinder, ReservationFinder reservationFinder) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeFinder = reservationTimeFinder;
        this.themeFinder = themeFinder;
        this.reservationFinder = reservationFinder;
    }

    public ReservationResponse recordReservationBy(ReservationRequest request) {
        boolean alreadyExisted = reservationFinder.doesReservationExist(request.timeId(), request.themeId());
        if (alreadyExisted) {
            throw new DuplicateReservationException();
        }

        final ReservationTime time = reservationTimeFinder.findOneById(request.timeId());
        final Theme theme = themeFinder.findOneById(request.themeId());
        final Reservation reservation = Reservation.of(request.name(), request.date(), time, theme);
        final Reservation saved = reservationRepository.save(reservation);

        return ReservationResponse.from(saved, theme, time);
    }
}
