package roomescape.application.service;

import org.springframework.stereotype.Service;
import roomescape.application.mapper.ThemeEntityMapper;
import roomescape.application.mapper.ThemeMapper;
import roomescape.application.service.command.CreateThemeCommand;
import roomescape.domain.theme.Theme;
import roomescape.domain.theme.Themes;
import roomescape.repository.ThemeRepository;
import roomescape.repository.entity.ThemeEntity;

@Service
public class ThemeService {

    private final ThemeRepository themeRepository;

    public ThemeService(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public Theme createTheme(CreateThemeCommand createThemeCommand) {
        Theme theme = createThemeCommand.toTheme();
        ThemeEntity themeEntity = themeRepository.save(ThemeEntityMapper.toThemeEntity(theme));

        return ThemeMapper.toTheme(themeEntity);
    }

    public Themes findAllThemes() {
        return ThemeMapper.toThemes(themeRepository.findAll());
    }
}
