package nextstep.schedule;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.SpringControllerTest;
import nextstep.theme.ThemeCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.time.LocalTime;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

class ScheduleControllerTest extends SpringControllerTest {

    @Autowired
    private ScheduleJdbcRepository scheduleJdbcRepository;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        scheduleJdbcRepository.clear();
    }

    @DisplayName("스케줄 생성")
    @Test
    void createSchedule() {
        // given
        ScheduleCreateRequest request = new ScheduleCreateRequest(1L, LocalDate.parse("2022-10-11"), LocalTime.parse("13:00:00"));

        // when
        ExtractableResponse<Response> response = 스케줄_생성_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private static ExtractableResponse<Response> 스케줄_생성_요청(ScheduleCreateRequest request) {
        return given()
                .body(request).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/schedules")
                .then().log().all().extract();
    }
}
