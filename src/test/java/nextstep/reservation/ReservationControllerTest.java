package nextstep.reservation;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.reservation.ReservationCreateRequest;
import nextstep.reservation.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        reservationRepository.clear();
    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        // given
        ReservationCreateRequest createRequest = new ReservationCreateRequest(
                LocalDate.parse("2022-10-11"),
                LocalTime.parse("13:00:00"),
                "박민영"
        );

        // when
        ExtractableResponse<Response> response = 예약_생성_요청(createRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        ExtractableResponse<Response> reservationsResponse = 예약_조회_요청("2022-10-11");
        assertThat(reservationsResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(reservationsResponse.jsonPath().getList("name")).contains("박민영");
    }

    @DisplayName("예약 조회")
    @Test
    void getReservation() {
        // given
        예약_생성_요청(new ReservationCreateRequest(
                LocalDate.parse("2022-10-11"),
                LocalTime.parse("13:00:00"),
                "박민영"
        ));
        예약_생성_요청(new ReservationCreateRequest(
                LocalDate.parse("2022-10-11"),
                LocalTime.parse("14:00:00"),
                "찰리"
        ));

        // when
        ExtractableResponse<Response> response = 예약_조회_요청("2022-10-11");

        //
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("name")).contains("박민영", "찰리");
    }

    @DisplayName("예약 취소")
    @Test
    void cancelReservation() {
        // given
        예약_생성_요청(new ReservationCreateRequest(
                LocalDate.parse("2022-10-11"),
                LocalTime.parse("13:00:00"),
                "박민영"
        ));
        ExtractableResponse<Response> reservationsResponse = 예약_조회_요청("2022-10-11");
        assertThat(reservationsResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(reservationsResponse.jsonPath().getList("name")).contains("박민영");

        // when
        ExtractableResponse<Response> response = 예약_삭제_요청("2022-10-11", "13:00");

        //
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 예약_생성_요청(ReservationCreateRequest request) {
        return given()
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 예약_조회_요청(String date) {
        return given()
                .queryParam("date", date)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 예약_삭제_요청(String date, String time) {
        return given()
                .queryParam("date", date)
                .queryParam("time", time)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations")
                .then().log().all().extract();
    }
}
