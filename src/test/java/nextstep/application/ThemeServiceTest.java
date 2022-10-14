package nextstep.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class ThemeServiceTest {

    @Autowired
    private ThemeService themeService;

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

    @DisplayName("삭제하려는 테마가 존재하지 않을 경우 예외가 발생한다.")
    @Test
    void themeDeleteException() {
        assertThatThrownBy(() -> themeService.deleteById(1L))
            .isExactlyInstanceOf(IllegalArgumentException.class)
            .hasMessage("ID 에 해당하는 테마가 없습니다.");
    }
}
