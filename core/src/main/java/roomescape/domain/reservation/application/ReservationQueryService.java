package roomescape.domain.reservation.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import roomescape.domain.reservation.application.dto.ReservationTimeThemeDto;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationRepository;
import roomescape.domain.reservationtime.domain.ReservationTime;
import roomescape.domain.reservationtime.domain.ReservationTimeId;
import roomescape.domain.reservationtime.domain.ReservationTimeRepository;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;
import roomescape.domain.theme.domain.ThemeRepository;

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
        final List<Reservation> reservations = reservationRepository.findAll();
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
