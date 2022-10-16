package nextstep.service;

import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static nextstep.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ThemeServiceTest extends ServiceTest {
    @Autowired
    private ThemeService themeService;

    @BeforeEach
    void setUp() {
        initThemeTable();
    }

    @Test
    @DisplayName("테마를 생성한다.")
    void createTheme() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);

        // when
        Long themeId = themeService.createTheme(request);

        // then
        assertThat(themeId).isNotNull();
    }

    @Test
    @DisplayName("전체 테마목록을 조회한다.")
    void findAllThemes() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        themeService.createTheme(request);

        // when
        ThemeFindAllResponse themes = themeService.findAllThemes();

        // then
        assertThat(themes.getThemes()).hasSize(1);
    }

    @Test
    @DisplayName("테마 ID로 테마를 삭제한다.")
    void deleteTheme() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        Long themeId = themeService.createTheme(request);

        // when, then
        assertDoesNotThrow(() -> themeService.deleteTheme(themeId));
    }
}
