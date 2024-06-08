package roomescape.apply.theme.application;

import org.springframework.stereotype.Service;
import roomescape.apply.theme.domain.repository.ThemeRepository;
import roomescape.apply.theme.application.exception.NotFoundThemeException;

@Service
public class ThemeDeleter {

    private final ThemeRepository themeRepository;

    public ThemeDeleter(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public void deleteThemeBy(long id) {
        final long existId = themeRepository.checkIdExists(id).orElseThrow(NotFoundThemeException::new);
        themeRepository.deleteById(existId);
    }
}
