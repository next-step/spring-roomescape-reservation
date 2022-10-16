package nextstep.service;

import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ThemeServiceTest {
    @Autowired
    private ThemeService themeService;

    @Test
    @DisplayName("테마를 생성한다.")
    void createTheme() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest("어마어마한 테마", "짱무서움", 20_000);

        // when
        Long themeId = themeService.createTheme(request);

        // then
        assertThat(themeId).isNotNull();
    }

    @Test
    @DisplayName("전체 테마목록을 조회한다.")
    void findAllThemes() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest("어마어마한 테마", "짱무서움", 20_000);
        themeService.createTheme(request);

        // when
        ThemeFindAllResponse themes = themeService.findAllThemes();

        // then
        assertThat(themes.getThemes()).hasSize(1);
    }
}
