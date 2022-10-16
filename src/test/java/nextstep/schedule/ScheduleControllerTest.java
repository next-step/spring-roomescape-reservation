package nextstep.schedule;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.SpringControllerTest;
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
import static nextstep.theme.ThemeControllerTest.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleControllerTest extends SpringControllerTest {

    @Autowired
    private ThemeJdbcRepository themeJdbcRepository;
    @Autowired
    private ScheduleJdbcRepository scheduleJdbcRepository;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        themeJdbcRepository.clear();
        scheduleJdbcRepository.clear();
    }

    @DisplayName("스케줄 생성")
    @Test
    void createSchedule() {
        // given
        long themeId = 테마를_생성한다("404호의 비밀");
        ScheduleCreateRequest request = new ScheduleCreateRequest(themeId, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"));

        // when
        ExtractableResponse<Response> response = 스케줄_생성_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("같은 테마, 같은 일시에 스케줄이 존재하면, 스케줄 생성에 실패한다.")
    @Test
    void createScheduleWithDuplicatedSchedule() {
        // given
        long themeId = 테마를_생성한다("404호의 비밀");

        ScheduleCreateRequest request1 = new ScheduleCreateRequest(themeId, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"));
        ExtractableResponse<Response> response1 = 스케줄_생성_요청(request1);
        assertThat(response1.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        ScheduleCreateRequest request2 = new ScheduleCreateRequest(themeId, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"));

        // when
        ExtractableResponse<Response> response2 = 스케줄_생성_요청(request2);

        // then
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @DisplayName("요청한 테마 ID로 조회되는 테마가 없으면 스케줄 생성에 실패한다.")
    @Test
    void createScheduleWithNonExistTheme() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"));

        // when
        ExtractableResponse<Response> response = 스케줄_생성_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @DisplayName("스케줄 조회")
    @Test
    void getSchedules() {
        // given
        long createdThemeId = 테마를_생성한다("404호의 비밀");
        long createdScheduleId = 스케줄을_생성한다(createdThemeId);

        // when
        ExtractableResponse<Response> response = 스케줄_조회_요청(createdThemeId, "2022-10-11");

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("date")).containsExactly("2022-10-11");
    }

    @DisplayName("스케줄 삭제")
    @Test
    void deleteSchedule() {
        // given
        long createdThemeId = 테마를_생성한다("404호의 비밀");
        long createdScheduleId = 스케줄을_생성한다(createdThemeId);
        ExtractableResponse<Response> getThemesResponse = 스케줄_조회_요청(createdThemeId, "2022-10-11");
        assertThat(getThemesResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getThemesResponse.jsonPath().getList("id")).hasSize(1);

        // when
        ExtractableResponse<Response> response = 스케줄_삭제_요청(createdScheduleId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        ExtractableResponse<Response> getThemesResponse2 = 스케줄_조회_요청(createdThemeId, "2022-10-11");
        assertThat(getThemesResponse2.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getThemesResponse2.jsonPath().getList("id")).isEmpty();
    }

    @DisplayName("스케줄이 존재하지 않으면 스케줄 삭제에 실패한다.")
    @Test
    void deleteScheduleWithNonExistScheduleId() {
        // given
        long createdThemeId = 테마를_생성한다("404호의 비밀");
        ExtractableResponse<Response> getThemesResponse = 스케줄_조회_요청(createdThemeId, "2022-10-11");
        assertThat(getThemesResponse.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(getThemesResponse.jsonPath().getList("id")).isEmpty();

        // when
        ExtractableResponse<Response> response = 스케줄_삭제_요청(1L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private static ExtractableResponse<Response> 스케줄_생성_요청(ScheduleCreateRequest request) {
        return given()
                .body(request).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/schedules")
                .then().log().all().extract();
    }

    public static long 스케줄을_생성한다(long themeId) {
        ScheduleCreateRequest request = new ScheduleCreateRequest(themeId, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"));
        ExtractableResponse<Response> response = 스케줄_생성_요청(request);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        return Long.parseLong(String.valueOf(response.header("Location").split("^*/")[2]));
    }

    public ExtractableResponse<Response> 스케줄_조회_요청(long themeId, String date) {
        return given()
                .queryParam("themeId", themeId)
                .queryParam("date", date)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/schedules")
                .then().log().all().extract();
    }

    public ExtractableResponse<Response> 스케줄_삭제_요청(long scheduleId) {
        return given()
                .pathParam("scheduleId", scheduleId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/schedules/{scheduleId}")
                .then().log().all().extract();
    }
}
