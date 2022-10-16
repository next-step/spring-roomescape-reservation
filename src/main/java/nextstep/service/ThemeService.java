package nextstep.service;

import nextstep.domain.Theme;
import nextstep.domain.ThemeRepository;
import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {
    public static final String DUPLICATE_THEME_MESSAGE = "이미 존재하는 테마이름입니다.";
    private final ThemeRepository themes;

    public ThemeService(ThemeRepository themes) {
        this.themes = themes;
    }

    public Long createTheme(ThemeCreateRequest themeCreateRequest) {
        String name = themeCreateRequest.getName();
        String desc = themeCreateRequest.getDesc();
        int price = themeCreateRequest.getPrice();

        checkThemeAvailable(name);
        Theme theme = themes.save(new Theme(name, desc, price));
        return theme.getId();
    }

    private void checkThemeAvailable(String name) {
        if (themes.existsByName(name)) {
            throw new IllegalArgumentException(DUPLICATE_THEME_MESSAGE);
        }
    }

    public ThemeFindAllResponse findAllThemes() {
        List<Theme> findThemes = themes.findAll();
        return ThemeFindAllResponse.from(findThemes);
    }

    public void deleteTheme(Long themeId) {
        // todo 예약이 있으면 테마 삭제 불가
        themes.deleteById(themeId);
    }
}
