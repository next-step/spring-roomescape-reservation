package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ErrorResponse;
import nextstep.dto.ScheduleCreateRequest;
import nextstep.dto.ScheduleFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static nextstep.Constants.*;
import static nextstep.service.ScheduleService.*;
import static org.assertj.core.api.Assertions.assertThat;

class ScheduleAcceptanceTest extends AcceptanceTest {
    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        initScheduleTable();
        initReservationTable();
    }

    @Test
    @DisplayName("POST 스케줄 생성")
    void createSchedule() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);

        // when
        ExtractableResponse<Response> response = createSchedule(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("POST 스케줄 생성 - 중복 생성 시, 생성 실패")
    void failToCreate() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);
        createSchedule(request);

        // when
        ExtractableResponse<Response> response = createSchedule(request);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(DUPLICATE_SCHEDULE_MESSAGE);
    }

    @Test
    @DisplayName("GET 스케줄 전체조회")
    void findAllSchedules() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);
        createSchedule(request);

        // when
        ExtractableResponse<Response> response = findAllSchedules(THEME_ID, DATE_STRING);
        ScheduleFindAllResponse scheduleFindAllResponse = response.as(ScheduleFindAllResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(scheduleFindAllResponse.getSchedules()).hasSize(1);
    }

    @Test
    @DisplayName("DELETE 스케줄 삭제")
    void deleteSchedule() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);
        String scheduleId = createSchedule(request).header("Location").split("/")[2];

        // when
        ExtractableResponse<Response> response = deleteSchedule(Long.parseLong(scheduleId));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("DELETE 스케줄 삭제 - 예약 존재 시, 삭제 실패")
    void failToDelete() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(THEME_ID, DATE_STRING, TIME_STRING);
        String scheduleId = createSchedule(request).header("Location").split("/")[2];
        예약생성();

        // when
        ExtractableResponse<Response> response = deleteSchedule(Long.parseLong(scheduleId));
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(CANT_DELETE_SCHEDULE);
    }

    private ExtractableResponse<Response> createSchedule(ScheduleCreateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/schedules")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> findAllSchedules(Long themeId, String date) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("themeId", themeId)
                .queryParam("date", date)
                .when().get("/schedules")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> deleteSchedule(Long id) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/schedules/" + id)
                .then().log().all().extract();
    }
}
