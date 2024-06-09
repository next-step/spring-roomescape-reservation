package roomescape.apply.theme.application;

import org.springframework.stereotype.Service;
import roomescape.apply.theme.domain.Theme;
import roomescape.apply.theme.domain.repository.ThemeRepository;
import roomescape.apply.theme.ui.dto.ThemeResponse;
import roomescape.apply.theme.application.exception.NotFoundThemeException;

import java.util.List;

@Service
public class ThemeFinder {

    private final ThemeRepository themeRepository;

    public ThemeFinder(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<ThemeResponse> findAll() {
        return themeRepository.findAll()
                .stream()
                .map(ThemeResponse::from)
                .toList();
    }

    public Theme findOneById(long themeId) {
        return themeRepository.findById(themeId).orElseThrow(NotFoundThemeException::new);
    }
}
