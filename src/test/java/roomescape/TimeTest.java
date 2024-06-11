package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.time.global.error.exception.ErrorCode;
import roomescape.time.global.error.exception.InvalidValueException;
import roomescape.time.presentation.dto.TimeRequest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeTest {

    private static final String RENDERING_TIME_URL = "/admin/time";
    private static final String TIMES_API_URL = "/times";
    private static final String PATH_VARIABLE_SUFFIX_URL = "/1";
    private static final String ID = "id";
    private static final String START_AT = "startAt";
    private static final String TIME = "15:40";
    private static final int ONE = 1;
    private static final long LONG_ONE = 1L;
    private static final String WILD_CARD = "$";

    @Test
    void 시간_관리_페이지를_랜더링한다() {

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get(RENDERING_TIME_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void 시간을_등록한다() {

        //given
        Map<String, Object> time = new HashMap<>();
        time.put(START_AT, TIME);

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(time)
                .when().post(TIMES_API_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.jsonPath().getLong(ID)).isEqualTo(LONG_ONE);
    }

    @Test
    void 시간을_조회한다() {

        //given
        시간을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get(TIMES_API_URL)
                .then().log().all()
                .extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.jsonPath().getList(WILD_CARD).size()).isEqualTo(ONE);
    }

    @Test
    void 시간을_삭제한다() {

        //given
        시간을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete(TIMES_API_URL + PATH_VARIABLE_SUFFIX_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"24:01", "-1:32", "12;30", "01:61"})
    void 시간_형식이_맞지_않는_경우_InvalidValueException_예외를_발생시킨다(String startAt) {

        //given
        TimeRequest timeRequest = new TimeRequest(startAt);

        //when, then
        Assertions.assertThatThrownBy(timeRequest::convertToDomainObject).isInstanceOf(InvalidValueException.class).hasMessage(ErrorCode.INVALID_TIME_FORMAT_ERROR.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"00:00", "12:00", "23:59"})
    void 시간_형식이_맞는_경우_예외가_발생하지_않는다(String startAt) {

        //given
        TimeRequest timeRequest = new TimeRequest(startAt);

        //when. then
        Assertions.assertThatCode(timeRequest::convertToDomainObject).doesNotThrowAnyException();
    }
}
