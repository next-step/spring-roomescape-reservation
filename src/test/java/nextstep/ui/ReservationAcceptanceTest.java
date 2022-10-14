package nextstep.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import io.restassured.RestAssured;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.ui.request.ReservationCreateRequest;
import nextstep.ui.request.ScheduleCreateRequest;
import nextstep.ui.request.ThemeCreateRequest;
import nextstep.ui.response.ReservationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ReservationAcceptanceTest extends AcceptanceTest {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        initReservationTable();
        initScheduleTable();
        initThemeTable();
    }

    @DisplayName("예약 생성 - POST /reservations")
    @Test
    void create() {
        Long themeId = 테마_생성();
        Long scheduleId = 스케줄_생성(
            themeId,
            LocalDate.of(2022, 10, 1),
            LocalTime.of(10, 1)
        );
        ReservationCreateRequest request = reservationCreateRequest(scheduleId, "최현구");

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(CREATED.value());
    }

    @DisplayName("예약 생성 시 스케줄에 예약이 이미 있는 경우 예약을 생성할 수 없다. - POST /reservations")
    @Test
    void duplicateException() {
        Long themeId = 테마_생성();
        Long scheduleId = 스케줄_생성(
            themeId,
            LocalDate.of(2022, 10, 1),
            LocalTime.of(10, 1)
        );
        예약_생성(scheduleId, "최현구");
        ReservationCreateRequest request = reservationCreateRequest(scheduleId, "최현구");

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .body(is("이미 해당 스케줄에 예약이 존재합니다."));
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

    @DisplayName("예약 삭제시 해당되는 예약이 없으면 삭제를 실패한다. - DELETE /reservations?date={date}&time={time}")
    @Test
    void deleteByDateTimeException() {
        RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("date", "2022-10-20")
            .queryParam("time", "13:00")
            .when().delete("/reservations")
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .body(is("시간과 날짜에 해당하는 예약정보가 없습니다."));
    }

    private Long 예약_생성(LocalDate date, LocalTime time, String name) {
        Long themeId = 테마_생성();
        Long scheduleId = 스케줄_생성(themeId, date, time);
        ReservationCreateRequest request = reservationCreateRequest(scheduleId, name);

        return Long.valueOf(RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(CREATED.value())
            .extract().header("Location")
            .split("/reservations/")[1]);
    }

    private Long 예약_생성(Long scheduleId, String name) {
        ReservationCreateRequest request = reservationCreateRequest(scheduleId, name);

        return Long.valueOf(RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(CREATED.value())
            .extract().header("Location")
            .split("/reservations/")[1]);
    }

    private ReservationCreateRequest reservationCreateRequest() {
        return reservationCreateRequest(1L, "최현구");
    }

    private ReservationCreateRequest reservationCreateRequest(Long scheduleId, String name) {
        return new ReservationCreateRequest(scheduleId, name);
    }

    private Long 스케줄_생성(Long themeId, LocalDate date, LocalTime time) {
        ScheduleCreateRequest request = scheduleCreateRequest(themeId, date, time);

        return Long.valueOf(RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/schedules")
            .then().log().all()
            .statusCode(CREATED.value())
            .extract().header("Location")
            .split("/schedules/")[1]);
    }

    private ScheduleCreateRequest scheduleCreateRequest(Long themeId, LocalDate date, LocalTime time) {
        return new ScheduleCreateRequest(themeId, date, time);
    }

    private Long 테마_생성() {
        ThemeCreateRequest request = themeCreateRequest();

        return Long.valueOf(RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/themes")
            .then().log().all()
            .statusCode(CREATED.value())
            .extract().header("Location")
            .split("/themes/")[1]);
    }

    private ThemeCreateRequest themeCreateRequest() {
        return new ThemeCreateRequest(
            "비밀의방",
            "기가막힌 방이랍니다~",
            BigDecimal.valueOf(50_000)
        );
    }
}
