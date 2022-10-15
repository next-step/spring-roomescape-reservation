package nextstep.service;

import nextstep.domain.Theme;
import nextstep.domain.ThemeRepository;
import nextstep.dto.ThemeCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class ThemeService {
    private final ThemeRepository themes;

    public ThemeService(ThemeRepository themes) {
        this.themes = themes;
    }

    public Long createTheme(ThemeCreateRequest themeCreateRequest) {
        String name = themeCreateRequest.getName();
        String desc = themeCreateRequest.getDesc();
        int price = themeCreateRequest.getPrice();

        Theme theme = themes.save(new Theme(name, desc, price));
        return theme.getId();
    }
}
