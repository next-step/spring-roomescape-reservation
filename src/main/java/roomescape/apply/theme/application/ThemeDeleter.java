package roomescape.apply.theme.application;

import org.springframework.stereotype.Service;
import roomescape.apply.reservation.application.ReservationFinder;
import roomescape.apply.theme.application.exception.ThemeReferencedException;
import roomescape.apply.theme.domain.repository.ThemeRepository;
import roomescape.apply.theme.application.exception.NotFoundThemeException;

import java.util.Optional;

@Service
public class ThemeDeleter {

    private final ThemeRepository themeRepository;
    private final ReservationFinder reservationFinder;

    public ThemeDeleter(ThemeRepository themeRepository, ReservationFinder reservationFinder) {
        this.themeRepository = themeRepository;
        this.reservationFinder = reservationFinder;
    }

    public void deleteThemeBy(long id) {
        final long existId = themeRepository.checkIdExists(id).orElseThrow(NotFoundThemeException::new);
        boolean isReferenced = reservationFinder.findAnyByThemeId(id).isPresent();
        if (isReferenced) {
            throw new ThemeReferencedException();
        }

        themeRepository.deleteById(existId);
    }
}
