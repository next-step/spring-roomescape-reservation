package nextstep.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE theme IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE theme("
            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "name VARCHAR(100) NOT NULL,"
            + "description TEXT NOT NULL,"
            + "price DECIMAL NOT NULL"
            + ")");
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
