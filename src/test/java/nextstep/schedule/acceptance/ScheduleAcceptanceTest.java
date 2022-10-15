package nextstep.schedule.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import io.restassured.RestAssured;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.AcceptanceTest;
import nextstep.schedule.ui.request.ScheduleCreateRequest;
import nextstep.schedule.ui.request.ThemeCreateRequest;
import nextstep.schedule.ui.response.ScheduleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ScheduleAcceptanceTest extends AcceptanceTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        initScheduleTable();
        initThemeTable();
    }

    @DisplayName("스케줄 생성 - POST /schedules")
    @Test
    void create() {
        Long themeId = 테마_생성();
        ScheduleCreateRequest request = scheduleCreateRequest(
            themeId,
            LocalDate.of(2022, 10, 1),
            LocalTime.of(10, 0)
        );

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/schedules")
            .then().log().all()
            .statusCode(CREATED.value());
    }

    @DisplayName("스케줄 생성시 해당 테마에 날짜와 시간이 똑같은 스케줄이 이미 있는 경우 예약을 생성할 수 없다. - POST /schedules")
    @Test
    void createException() {
        Long themeId = 테마_생성();
        LocalDate date = LocalDate.of(2022, 10, 1);
        LocalTime time = LocalTime.of(10, 0);
        스케줄_생성(themeId, date, time);

        ScheduleCreateRequest request = scheduleCreateRequest(themeId, date, time);

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/schedules")
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .body(is("테마에 날짜와 시간이 똑같은 스케줄이 이미 존재합니다."));
    }

    @DisplayName("테마와 날짜에 따른 스케줄 목록 조회 - GET /schedules?themeId={themeId}&date={date}")
    @Test
    void getSchedulesByThemeIdAndDate() {
        Long themeId = 테마_생성();
        LocalDate date = LocalDate.of(2022, 10, 1);
        LocalTime time = LocalTime.of(10, 0);
        스케줄_생성(themeId, date, time);

        List<ScheduleResponse> results = RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/schedules?themeId=" + themeId + "&date=" + date)
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().jsonPath().getList(".", ScheduleResponse.class);

        assertThat(results).hasSize(1);
    }

    @DisplayName("스케줄 삭제 - DELETE /schedules/{scheduleId}")
    @Test
    void deleteById() {
        Long themeId = 테마_생성();
        LocalDate date = LocalDate.of(2022, 10, 1);
        LocalTime time = LocalTime.of(10, 0);
        Long scheduleId = 스케줄_생성(themeId, date, time);

        RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/schedules/" + scheduleId)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("스케줄 삭제시 해당되는 스케줄이 없으면 삭제를 실패한다. - DELETE /schedules/{scheduleId}")
    @Test
    void deleteByIdException() {
        RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/schedules/999")
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .body(is("ID 에 해당하는 스케줄이 없습니다."));
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
