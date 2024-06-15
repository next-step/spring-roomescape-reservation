package roomescape.reservationTheme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@JdbcTest
class ReservationThemeRepositoryTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private ReservationThemeRepository reservationThemeRepository;

    @BeforeEach
    void setUp() {

        reservationThemeRepository = new ReservationThemeRepository(jdbcTemplate);

        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS theme");

        jdbcTemplate.execute("""
                CREATE TABLE theme (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            description VARCHAR(255) NOT NULL,
                            thumbnail VARCHAR(255) NOT NULL,
                            PRIMARY KEY (id))
                """);
    }


    @DisplayName("테마를 저장할 수 있다.")
    @Test
    void save() {
        //given
        final ReservationTheme theme = new ReservationTheme.Builder()
                .name("쏘우1")
                .description("게임을 시작하지~! (-_-)b")
                .thumbnail("https://soo1.com")
                .build();

        final Long id = reservationThemeRepository.save(theme);

        //when
        final ReservationTheme savedTheme = reservationThemeRepository.findById(id);

        //then
        assertThat(savedTheme.getName()).isEqualTo(theme.getName());
    }

    @DisplayName("모든 테마를 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        final ReservationTheme theme1 = new ReservationTheme.Builder()
                .name("쏘우1")
                .description("게임을 시작하지~! (-_-)b")
                .thumbnail("https://soo1.com")
                .build();

        final ReservationTheme theme2 = new ReservationTheme.Builder()
                .name("쏘우2")
                .description("게임을 시작안하지~! (-_-)b")
                .thumbnail("https://soo2.com")
                .build();

        reservationThemeRepository.save(theme1);
        reservationThemeRepository.save(theme2);

        //when
        final List<ReservationTheme> savedThemes = reservationThemeRepository.findAll();

        //then
        assertThat(savedThemes.size()).isEqualTo(2);

    }

    @DisplayName("테마를 조회할 수 있다.")
    @Test
    void findById() {

        //given
        final ReservationTheme theme = new ReservationTheme.Builder()
                .name("쏘우1")
                .description("게임을 시작하지~! (-_-)b")
                .thumbnail("https://soo1.com")
                .build();

        final Long id = reservationThemeRepository.save(theme);

        //when
        final ReservationTheme savedTheme = reservationThemeRepository.findById(id);

        //then
        assertThat(savedTheme.getName()).isEqualTo(theme.getName());
    }

    @DisplayName("테마를 삭제할 수 있다.")
    @Test
    void deleteById() {
        //given
        final ReservationTheme theme = new ReservationTheme.Builder()
                .name("쏘우1")
                .description("게임을 시작하지~! (-_-)b")
                .thumbnail("https://soo1.com")
                .build();

        final Long id = reservationThemeRepository.save(theme);

        //when
        reservationThemeRepository.deleteById(id);

        //then
        assertThat(reservationThemeRepository.existById(id)).isFalse();
    }

    @DisplayName("테마가 존재하는지 확인할 수 있다.")
    @Test
    void existById() {
        //given
        final ReservationTheme theme = new ReservationTheme.Builder()
                .name("쏘우1")
                .description("게임을 시작하지~! (-_-)b")
                .thumbnail("https://soo1.com")
                .build();

        final Long id = reservationThemeRepository.save(theme);

        //when
        final boolean isExisted = reservationThemeRepository.existById(id);

        //then
        assertThat(isExisted).isTrue();
    }

}
