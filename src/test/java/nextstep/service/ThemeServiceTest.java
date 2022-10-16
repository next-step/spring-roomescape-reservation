package nextstep.service;

import nextstep.dto.ThemeCreateRequest;
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
}
