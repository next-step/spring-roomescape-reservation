package nextstep.app.web.theme.port.query;

import nextstep.domain.theme.domain.model.Theme;
import nextstep.domain.theme.domain.model.ThemeRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ThemeQuery {
    private final ThemeRepository themeRepository;

    public ThemeQuery(ThemeRepository themeRepository) {
        this.themeRepository = themeRepository;
    }

    public List<Theme> findAll() {
        return themeRepository.findAll();
    }
}
