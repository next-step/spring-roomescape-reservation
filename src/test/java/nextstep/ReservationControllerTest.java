package nextstep;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private ReservationDatabase reservationDatabase;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        reservationDatabase.reservations.clear();
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
        assertThat(response.header("Location")).isEqualTo("/reservations/1");

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
        ExtractableResponse<Response> reservationsResponse = 예약_조회_요청("2022-10-11");

        //
        assertThat(reservationsResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(reservationsResponse.jsonPath().getList("name")).contains("박민영", "찰리");
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
}
