package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.time.error.exception.ErrorCode;
import roomescape.time.error.exception.TimeException;
import roomescape.time.presentation.dto.TimeRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeTest {

    private static final String ID = "id";
    private static final String START_AT = "startAt";

    @Test
    void 시간_관리_페이지를_랜더링한다() {

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void 시간을_등록한다() {

        //given
        Map<String, Object> time = new HashMap<>();
        time.put(START_AT, "15:40");

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(time)
                .when().post("/times")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong(ID)).isEqualTo(1L);
    }

    @Test
    void 시간을_조회한다() {

        //given
        시간을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("$").size()).isEqualTo(1);
    }

    @Test
    void 시간을_삭제한다() {

        //given
        시간을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"24:01", "-1:32", "12;30", "01:61"})
    void 시간_형식이_맞지_않는_경우_예외를_발생시킨다(String startAt) {

        //given
        TimeRequest timeRequest = new TimeRequest(startAt);

        //when, then
        assertThatThrownBy(timeRequest::convertToDomainObject).isInstanceOf(TimeException.class).hasMessage(ErrorCode.INVALID_TIME_FORMAT_ERROR.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"00:00", "12:00", "23:59"})
    void 시간_형식이_맞는_경우_예외가_발생하지_않는다(String startAt) {

        //given
        TimeRequest timeRequest = new TimeRequest(startAt);

        //when, then
        assertThatCode(timeRequest::convertToDomainObject).doesNotThrowAnyException();
    }
}
