package roomescape.theme.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import roomescape.theme.domain.Theme;

@JdbcTest
class JdbcThemeRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private JdbcThemeRepository jdbcThemeRepository;

    @BeforeEach
    void setUp() {
        jdbcThemeRepository = new JdbcThemeRepository(jdbcTemplate, dataSource);
    }

    @Test
    @DisplayName("테마를 저장한다.")
    void save() {
        // given
        Theme theme = new Theme("레벨2 탈출","우테코 레벨2를 탈출하는 내용입니다.",
                "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");

        // when
        Theme savedTheme = jdbcThemeRepository.save(theme);

        // then
        assertThat(savedTheme.getId()).isNotNull();
        assertThat(savedTheme).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(theme);
    }

    @Test
    @DisplayName("모든 테마를 조회한다.")
    void findAll() {
        // given
        Theme theme = new Theme("레벨2 탈출","우테코 레벨2를 탈출하는 내용입니다.",
                "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
        jdbcThemeRepository.save(theme);

        // when
        List<Theme> themes = jdbcThemeRepository.findAll();

        // then
        assertThat(themes).hasSize(1);
    }

    @Test
    @DisplayName("테마가 존재하면 TRUE를 반환한다.")
    void existsById_ReturnTrue() {
        // given
        Theme theme = new Theme("레벨2 탈출","우테코 레벨2를 탈출하는 내용입니다.",
                "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");
        Theme savedTheme = jdbcThemeRepository.save(theme);

        // when
        boolean result = jdbcThemeRepository.existsById(savedTheme.getId());

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("테마가 존재하면 FALSE를 반환한다.")
    void existsById_ReturnFalse() {
        boolean result = jdbcThemeRepository.existsById(1L);

        assertThat(result).isFalse();
    }
}
