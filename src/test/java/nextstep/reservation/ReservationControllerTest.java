package nextstep.reservation;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.SpringControllerTest;
import nextstep.schedule.Schedule;
import nextstep.schedule.ScheduleControllerTest;
import nextstep.schedule.ScheduleJdbcRepository;
import nextstep.theme.ThemeControllerTest;
import nextstep.theme.ThemeJdbcRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static nextstep.schedule.ScheduleControllerTest.*;
import static nextstep.theme.ThemeControllerTest.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ReservationControllerTest extends SpringControllerTest {

    @Autowired
    private ThemeJdbcRepository themeJdbcRepository;
    @Autowired
    private ScheduleJdbcRepository scheduleJdbcRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        themeJdbcRepository.clear();
        scheduleJdbcRepository.clear();
        reservationRepository.clear();
    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {
        // given
        Long createdThemeId = 테마를_생성한다("404호의 비밀");
        long createdScheduleId = 스케줄을_생성한다(createdThemeId);
        ReservationCreateRequest createRequest = new ReservationCreateRequest(createdScheduleId, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"), "박민영");

        // when
        ExtractableResponse<Response> response = 예약_생성_요청(createRequest);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        ExtractableResponse<Response> reservationsResponse = 예약_조회_요청(createdScheduleId, "2022-10-11");
        assertThat(reservationsResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(reservationsResponse.jsonPath().getList("name")).contains("박민영");
    }

    @DisplayName("예약 조회")
    @Test
    void getReservation() {
        // given
        Long createdThemeId = 테마를_생성한다("404호의 비밀");
        long createdScheduleId = 스케줄을_생성한다(createdThemeId);
        예약_생성_요청(new ReservationCreateRequest(createdScheduleId, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"), "박민영"));
        예약_생성_요청(new ReservationCreateRequest(createdScheduleId, LocalDate.parse("2022-10-11"), LocalTime.parse("14:00:00"), "찰리"));

        // when
        ExtractableResponse<Response> response = 예약_조회_요청(createdScheduleId, "2022-10-11");

        //
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("name")).contains("박민영", "찰리");
    }

    @DisplayName("예약 취소")
    @Test
    void cancelReservation() {
        // given
        Long createdThemeId = 테마를_생성한다("404호의 비밀");
        long createdScheduleId = 스케줄을_생성한다(createdThemeId);
        예약_생성_요청(new ReservationCreateRequest(createdScheduleId, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"), "박민영"));
        ExtractableResponse<Response> reservationsResponse = 예약_조회_요청(createdScheduleId, "2022-10-11");
        assertThat(reservationsResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(reservationsResponse.jsonPath().getList("name")).contains("박민영");

        // when
        ExtractableResponse<Response> response = 예약_삭제_요청(createdScheduleId, "2022-10-11", "13:00");

        //
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ExtractableResponse<Response> 예약_생성_요청(ReservationCreateRequest request) {
        return given()
                .body(request).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservations")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 예약_조회_요청(Long scheduleId, String date) {
        return given()
                .queryParam("scheduleId", scheduleId)
                .queryParam("date", date)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservations")
                .then().log().all().extract();
    }

    public static ExtractableResponse<Response> 예약_삭제_요청(Long scheduleId, String date, String time) {
        return given()
                .queryParam("scheduleId", scheduleId)
                .queryParam("date", date)
                .queryParam("time", time)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservations")
                .then().log().all().extract();
    }
}
