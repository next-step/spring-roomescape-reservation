package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.service.command.CreateThemeCommand;
import roomescape.application.service.command.DeleteThemeCommand;
import roomescape.application.service.mapper.ThemeEntityMapper;
import roomescape.application.service.mapper.ThemeMapper;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.Themes;
import roomescape.repository.ThemeRepository;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme create(CreateThemeCommand command) {
        Theme theme = command.toTheme();

        return ThemeMapper.toTheme(
                themeRepository.save(ThemeEntityMapper.toThemeEntity(theme))
        );
    }

    public Themes findAll() {
        return ThemeMapper.toThemes(themeRepository.findAll());
    }

    public void delete(DeleteThemeCommand command) {
        themeRepository.delete(command.getThemeId());
    }
}
