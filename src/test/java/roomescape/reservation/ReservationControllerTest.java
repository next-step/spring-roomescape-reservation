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
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimePolicy;
import roomescape.reservationTime.ReservationTimeRequestDto;
import roomescape.reservationTime.ReservationTimeResponseDto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {

    @Autowired
    private ReservationTimePolicy reservationTimePolicy;
    @Autowired
    private ReservationPolicy reservationPolicy;

    private ReservationRepository reservationRepository;
    private Long time1Id;
    private Long time2Id;
    private String time1 = "";
    private String time2 = "";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        reservationRepository = new ReservationRepository(jdbcTemplate, reservationPolicy, reservationTimePolicy);
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation");
        jdbcTemplate.execute("DROP TABLE IF EXISTS reservation_time");

        jdbcTemplate.execute(
                """
                        CREATE TABLE reservation_time (
                            id BIGINT NOT NULL AUTO_INCREMENT, 
                            start_at VARCHAR(255) NOT NULL,
                            PRIMARY KEY (id)
                        )
                        """
        );

        jdbcTemplate.execute(
                """
                        CREATE TABLE reservation (
                            id BIGINT NOT NULL AUTO_INCREMENT, 
                            name VARCHAR(255) NOT NULL, 
                            date VARCHAR(255) NOT NULL, 
                            time_id BIGINT,
                            PRIMARY KEY (id),
                            FOREIGN KEY (time_id) REFERENCES reservation_time (id)
                        )
                        """
        );

        final ReservationTime request1 = new ReservationTime("15:40", reservationTimePolicy);
        final ReservationTime request2 = new ReservationTime("16:40", reservationTimePolicy);
        List<Object[]> reservationTimes = Arrays.asList(request1, request2).stream()
                .map(reservationTime -> new Object[]{reservationTime.getStartAt()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO reservation_time(start_at) VALUES (?)", reservationTimes);

        var response1 = RestAssured
                .given().log().all()
                .when().get("/times")
                .then().log().all().extract();

        List<ReservationTimeResponseDto> timeResponseDtos = response1.jsonPath().getList(".", ReservationTimeResponseDto.class);
        time1Id = timeResponseDtos.get(0).getId();
        time2Id = timeResponseDtos.get(1).getId();
        time1 = timeResponseDtos.get(0).getStartAt();
        time2 = timeResponseDtos.get(1).getStartAt();

    }

    @DisplayName("전체 예약을 조회 합니다.")
    @Test
    void readReservation() {


        final Reservation reservation1 = new Reservation("제이슨", "2023-08-05", new ReservationTime(time1Id), reservationPolicy);
        final Reservation reservation2 = new Reservation("심슨", "2023-08-05", new ReservationTime(time2Id), reservationPolicy);

        List<Object[]> reservations = Arrays.asList(reservation1, reservation2).stream()
                .map(reservation -> new Object[]{reservation.getName(), reservation.getDate(), reservation.getReservationTime().getId()})
                .collect(Collectors.toList());

        jdbcTemplate.batchUpdate("INSERT INTO reservation(name, date, time_id) VALUES (?,?,?)", reservations);

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
        final ReservationRequestDto request = new ReservationRequestDto("제이슨", "2025-08-05", new ReservationTimeRequestDto(time1Id, time1));

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
        assertThat(responseDto.getReservationTimeResponseDto().getStartAt()).isEqualTo(request.getReservationTimeRequestDto().getStartAt());
    }

    @DisplayName("예약을 삭제합니다.")
    @Test
    void deleteReservation() {
        // given
        final ReservationRequestDto request = new ReservationRequestDto("제이슨", "2024-08-05",
                new ReservationTimeRequestDto(time1Id, time1));

        // when
        var response1 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();

        // when
        var response2 = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().delete("/reservations/" + response1.as(ReservationResponseDto.class).getId())
                .then().log().all().extract();

        // then
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
