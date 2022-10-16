package nextstep.service;

import nextstep.domain.Theme;
import nextstep.domain.ThemeRepository;
import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ThemeFindAllResponse findAllThemes() {
        List<Theme> findThemes = themes.findAll();
        return ThemeFindAllResponse.from(findThemes);
    }

    public void deleteTheme(Long themeId) {
        // todo 예약이 있으면 테마 삭제 불가
        themes.deleteById(themeId);
    }
}
