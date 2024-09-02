package roomescape.core.api.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.core.api.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.core.domain.reservation.Reservation;
import roomescape.core.domain.reservation.ReservationId;
import roomescape.core.domain.reservation.ReservationRepository;
import roomescape.core.domain.reservationtime.ReservationTime;
import roomescape.core.domain.reservationtime.ReservationTimeId;
import roomescape.core.domain.reservationtime.ReservationTimeRepository;
import roomescape.core.domain.theme.Theme;
import roomescape.core.domain.theme.ThemeId;
import roomescape.core.domain.theme.ThemeRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

    private final ReservationRepository reservationRepository;
    private final ThemeRepository themeRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public List<ReservationTimeThemeDto> fetchReservationThemes() {
        final List<Reservation> reservations = reservationRepository.findNotDeletedReservations();
        final Map<ThemeId, Theme> themes = findThemes(reservations);
        final Map<ReservationTimeId, ReservationTime> times = findTimes(reservations);

        return reservations.stream()
                .map(reservation -> ReservationTimeThemeDto.of(
                        reservation,
                        times.get(reservation.getTimeId()),
                        themes.get(reservation.getThemeId())
                ))
                .toList();
    }

    public ReservationTimeThemeDto fetchReservationTimeThemeBy(final ReservationId reservationId) {
        final Reservation reservation = reservationRepository.getById(reservationId.value());
        final Map<ReservationTimeId, ReservationTime> times = findTimes(List.of(reservation));
        final Map<ThemeId, Theme> themes = findThemes(List.of(reservation));

        return ReservationTimeThemeDto.of(reservation, times.get(reservation.getTimeId()), themes.get(reservation.getThemeId()));
    }

    private Map<ReservationTimeId, ReservationTime> findTimes(final List<Reservation> reservations) {
        final List<ReservationTimeId> reservationTimeIds = reservations.stream().map(Reservation::getTimeId).toList();
        final List<ReservationTime> times = reservationTimeRepository.findAllByIds(reservationTimeIds);

        return times.stream().collect(Collectors.toMap(ReservationTime::getId, time -> time));
    }

    private Map<ThemeId, Theme> findThemes(final List<Reservation> reservations) {
        final List<ThemeId> themeIds = reservations.stream().map(Reservation::getThemeId).toList();
        final List<Theme> themes = themeRepository.findAllByIds(themeIds);

        return themes.stream().collect(Collectors.toMap(Theme::getId, theme -> theme));
    }
}
