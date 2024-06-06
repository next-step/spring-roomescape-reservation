package roomescape.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {

    private ReservationRepository reservationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate);
        jdbcTemplate.execute("DROP TABLE reservation IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE reservation(" +
                "id BIGINT AUTO_INCREMENT, " +
                "name VARCHAR(255) NOT NULL, " +
                "date VARCHAR(255), " +
                "time VARCHAR(255), " +
                "PRIMARY KEY (id))");
    }

    @DisplayName("전체 예약을 조회 합니다.")
    @Test
    void readReservation() {

        // given
        final Reservation reservation1 = new Reservation("제이슨", "2023-08-05", "15:40");
        final Reservation reservation2 = new Reservation("심슨", "2023-08-05", "15:40");
        List<Object[]> reservations = Arrays.asList(reservation1, reservation2).stream()
                .map(reservation -> new Object[]{reservation.getName(), reservation.getDate(), reservation.getTime()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO reservation(name, date, time) VALUES (?,?,?)", reservations);

        var response = RestAssured
                .given().log().all()
                .when().get("/reservations")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList(".", ReservationResponseDto.class)).hasSize(2);
    }

    @DisplayName("예약을 생성합니다.")
    @Test
    void createReservation() {
        // given
        final ReservationRequestDto request = new ReservationRequestDto("제이슨", "2023-08-05", "15:40");

        // when
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ReservationResponseDto responseDto = response.as(ReservationResponseDto.class);
        assertThat(responseDto.getName()).isEqualTo(request.getName());
        assertThat(responseDto.getDate()).isEqualTo(request.getDate());
        assertThat(responseDto.getTime()).isEqualTo(request.getTime());
    }

    @DisplayName("예약을 삭제합니다.")
    @Test
    void deleteReservation(){
        // given
        final ReservationRequestDto request = new ReservationRequestDto("메이븐", "2023-08-05", "15:40");

        // when
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ReservationResponseDto responseDto = response.as(ReservationResponseDto.class);
        assertThat(responseDto.getName()).isEqualTo(request.getName());
        assertThat(responseDto.getDate()).isEqualTo(request.getDate());
        assertThat(responseDto.getTime()).isEqualTo(request.getTime());

        // when
        var response2 = RestAssured.given().log().all()
                .when().delete("/reservations/"+responseDto.getId())
                .then().log().all().extract();

        // then
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
