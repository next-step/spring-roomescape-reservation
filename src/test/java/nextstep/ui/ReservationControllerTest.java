package nextstep.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import io.restassured.RestAssured;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.ui.request.ReservationCreateRequest;
import nextstep.ui.response.ReservationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 생성 - POST /reservations")
    @Test
    void create() {
        ReservationCreateRequest request = reservationCreateRequest();

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(CREATED.value());
    }

    @DisplayName("예약 생성 시 날짜와 시간이 똑같은 예약이 이미 있는 경우 예약을 생성할 수 없다. - POST /reservations")
    @Test
    void duplicateException() {
        LocalDate date = LocalDate.of(2022, 10, 15);
        LocalTime time = LocalTime.of(13, 0);
        예약_생성(date, time, "최현구");
        ReservationCreateRequest request = reservationCreateRequest(date, time, "브라운");

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .body(is("날짜와 시간이 똑같은 예약이 이미 존재합니다."));
    }

    @DisplayName("예약 조회 - GET /reservations?date={date}")
    @Test
    void getByDate() {
        LocalDate date = LocalDate.of(2022, 10, 25);
        LocalTime time = LocalTime.of(13, 0);
        String name = "최현구";
        예약_생성(date, time, name);

        List<ReservationResponse> results = RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("date", "2022-10-25")
            .when().get("/reservations")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().jsonPath().getList(".", ReservationResponse.class);

        assertThat(results).hasSize(1);
    }

    @DisplayName("예약 삭제 - DELETE /reservations?date={date}&time={time}")
    @Test
    void deleteByDateTime() {
        LocalDate date = LocalDate.of(2022, 10, 20);
        LocalTime time = LocalTime.of(13, 0);
        String name = "최현구";
        예약_생성(date, time, name);

        RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("date", "2022-10-20")
            .queryParam("time", "13:00")
            .when().delete("/reservations")
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    private void 예약_생성(LocalDate date, LocalTime time, String name) {
        ReservationCreateRequest request = reservationCreateRequest(date, time, name);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(CREATED.value());
    }

    private ReservationCreateRequest reservationCreateRequest() {
        return reservationCreateRequest(
            LocalDate.of(2022, 10, 11),
            LocalTime.of(13, 0),
            "최현구"
        );
    }

    private ReservationCreateRequest reservationCreateRequest(
        LocalDate date,
        LocalTime time,
        String name
    ) {
        return new ReservationCreateRequest(date, time, name);
    }
}
