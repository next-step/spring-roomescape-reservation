package roomescape.domain.theme.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import roomescape.domain.reservation.domain.Reservation;
import roomescape.domain.reservation.domain.ReservationRepository;
import roomescape.domain.theme.application.request.ThemeAppendRequest;
import roomescape.domain.theme.domain.Theme;
import roomescape.domain.theme.domain.ThemeId;
import roomescape.domain.theme.domain.ThemeRepository;
import roomescape.domain.theme.exception.ThemeAlreadyInUseException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThemeService {

    private final ThemeRepository themeRepository;
    private final ReservationRepository reservationRepository;

    public List<Theme> findAll() {
        return themeRepository.findNotDeletedThemes();
    }

    public Theme appendTheme(final ThemeAppendRequest request) {
        return themeRepository.save(request.toTheme());
    }

    public void deleteTheme(final ThemeId themeId) {
        verifyThemeNotInUse(themeId);

        final Theme theme = themeRepository.getById(themeId);
        final Theme deleted = theme.delete();
        themeRepository.save(deleted);
    }

    private void verifyThemeNotInUse(final ThemeId themeId) {
        final List<Reservation> reservations = reservationRepository.findAllByThemeId(themeId);
        if (!CollectionUtils.isEmpty(reservations)) {
            throw ThemeAlreadyInUseException.from(themeId);
        }
    }
}
