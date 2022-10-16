package nextstep.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ThemeRepositoryTest {
    @Autowired
    private ThemeRepository themes;

    @Test
    @DisplayName("테마를 저장한다.")
    void save() {
        // given
        Theme theme = new Theme("어마어마한 테마", "짱무서움", 20_000);

        // when
        Theme savedTheme = themes.save(theme);

        // then
        assertThat(savedTheme.getId()).isNotNull();
        assertThat(savedTheme).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(theme);
    }

    @Test
    @DisplayName("전체 테마를 조회한다.")
    void findAll() {
        // given
        Theme theme = themes.save(new Theme("어마어마한 테마", "짱무서움", 20_000));

        // when
        List<Theme> findThemes = themes.findAll();

        // then
        assertThat(findThemes).hasSize(1);
        assertThat(findThemes.get(0)).usingRecursiveComparison()
                .isEqualTo(theme);
    }

    @Test
    @DisplayName("테마 ID에 해당하는 테마를 삭제한다.")
    void deleteById() {
        // given
        Theme theme = themes.save(new Theme("어마어마한 테마", "짱무서움", 20_000));

        // when, then
        assertDoesNotThrow(() -> themes.deleteById(theme.getId()));
    }
}
