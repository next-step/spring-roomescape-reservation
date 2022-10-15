package nextstep.schedule.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import nextstep.RepositoryTest;
import nextstep.schedule.domain.Theme;
import nextstep.schedule.domain.ThemeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ThemeRepositoryTest extends RepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @BeforeEach
    void setUp() {
        initThemeTable();
    }

    @DisplayName("테마 저장")
    @Test
    void save() {
        Theme theme = new Theme("비밀의 방", "비밀의 방에서 탈출하세요!", BigDecimal.valueOf(50_000));

        Theme saved = themeRepository.save(theme);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved).usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(theme);
    }

    @DisplayName("ID 를 통한 테마 조회")
    @Test
    void findById() {
        Theme 비밀의방 = saveTheme("비밀의 방", "탈출해라", 50_000);

        Theme found = themeRepository.findById(비밀의방.getId());

        assertThat(found).isEqualTo(비밀의방);
    }

    @DisplayName("모든 테마 조회")
    @Test
    void findAll() {
        Theme 비밀의방 = saveTheme("비밀의 방", "탈출해라", 50_000);
        Theme 진실의방 = saveTheme("진실의 방", "진짜야", 50_000);

        List<Theme> themes = themeRepository.findAll();

        assertThat(themes).containsExactly(비밀의방, 진실의방);
    }

    @DisplayName("테마 삭제")
    @Test
    void deleteById() {
        Theme 비밀의방 = saveTheme("비밀의 방", "탈출해라", 50_000);

        int count = themeRepository.deleteById(비밀의방.getId());

        assertThat(count).isEqualTo(1);
        assertThat(themeRepository.findAll()).isEmpty();
    }

    private Theme saveTheme(String name, String description, long price) {
        return themeRepository.save(new Theme(name, description, BigDecimal.valueOf(price)));
    }
}
