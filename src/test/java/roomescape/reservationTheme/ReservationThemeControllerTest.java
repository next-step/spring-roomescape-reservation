package roomescape.reservationTheme;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.ReservationRepository;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimeResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationThemeControllerTest {

    private ReservationThemeRepository reservationThemeRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

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

    @DisplayName("모든 테마를 조회할 수 있다.")
    @Test
    void getThemes() {
        //given
        final ReservationThemeRequestDto requestDto1 = new ReservationThemeRequestDto("쏘우1", "게임을 시작하지~! (-_-)b", "https://soo1.com");


        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestDto1)
                .when().post("/themes")
                .then().log().all().extract();

        // when
        var themes = RestAssured.given().log().all()
                .when().get("/themes")
                .then().log().all().extract().as(ReservationThemeResponseDto[].class);

        // then
        assertThat(requestDto1.getName()).isEqualTo(themes[0].getName());
        assertThat(requestDto1.getDescription()).isEqualTo(themes[0].getDescription());
    }

    @DisplayName("테마를 저장할 수 있다.")
    @Test
    void createTheme() {

        //given
        final ReservationThemeRequestDto requestDto1 = new ReservationThemeRequestDto("쏘우1", "게임을 시작하지~! (-_-)b", "https://soo1.com");

        //when
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestDto1)
                .when().post("/themes")
                .then().log().all().extract();

        //then
        assertThat(response.statusCode()).isEqualTo(201);
        assertThat(response.jsonPath().getString("name")).isEqualTo(requestDto1.getName());
    }

    @DisplayName("테마를 삭제할 수 있다.")
    @Test
    void deleteTheme() {
        //given
        final ReservationThemeRequestDto requestDto1 = new ReservationThemeRequestDto("쏘우1", "게임을 시작하지~! (-_-)b", "https://soo1.com");

        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(requestDto1)
                .when().post("/themes")
                .then().log().all().extract();

        //when
        var response2 = RestAssured.given().log().all()
                .when().delete("/themes/1")
                .then().log().all().extract();

        //then
        assertThat(response2.statusCode()).isEqualTo(204);
    }
}
