package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ScheduleCreateRequest;
import nextstep.dto.ScheduleFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ScheduleControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("POST 예약 생성")
    void createReservation() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, "2020-12-01", "12:01");

        // when
        ExtractableResponse<Response> response = createSchedule(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("GET 스케줄 전체조회")
    void findAllSchedules() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, "2020-12-02", "12:02");
        createSchedule(request);

        // when
        ExtractableResponse<Response> response = findAllSchedules(1L, "2020-12-02");
        ScheduleFindAllResponse scheduleFindAllResponse = response.as(ScheduleFindAllResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(scheduleFindAllResponse.getSchedules()).hasSize(1);
    }

    @Test
    @DisplayName("DELETE 스케줄 삭제")
    void deleteSchedule() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, "2020-12-03", "12:03");
        String scheduleId = createSchedule(request).header("Location").split("/")[2];

        // when
        ExtractableResponse<Response> response = deleteSchedule(Long.parseLong(scheduleId));

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
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
