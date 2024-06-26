package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.service.command.CreateThemeCommand;
import roomescape.application.service.command.DeleteThemeCommand;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.ThemeRepository;
import roomescape.domain.theme.Themes;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme create(CreateThemeCommand command) {
        Theme theme = command.toTheme();

        return themeRepository.save(theme);
    }

    public Themes findAll() {
        return themeRepository.findAll();
    }

    public void delete(DeleteThemeCommand command) {
        themeRepository.delete(command.getThemeId());
    }
}
