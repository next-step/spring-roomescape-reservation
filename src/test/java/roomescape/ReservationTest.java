package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {

    private static final String RENDERING_RESERVATION_URL = "/admin/reservation";
    private static final String TIMES_API_URL = "/times";
    private static final String RESERVATIONS_API_URL = "/reservations";
    private static final String PATH_VARIABLE_SUFFIX_URL = "/1";
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String TIME_ID = "timeId";
    private static final String START_AT = "startAt";
    private static final String RESERVATION_NAME = "john";
    private static final String RESERVATION_DATE = "2023-08-05";
    private static final String TIME = "15:40";
    private static final int ONE = 1;
    private static final long LONG_ONE = 1L;
    private static final String WILD_CARD = "$";

    @BeforeEach
    void 시간_예약을_추가한다() {

        //given
        Map<String, Object> time = new HashMap<>();
        time.put(ID, ONE);
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
        Assertions.assertThat(response.jsonPath().getLong(ID)).isEqualTo(ONE);
    }

    @Test
    void 예약_관리_페이지를_랜더링한다() {

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get(RENDERING_RESERVATION_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void 예약을_등록한다() {

        //given
        Map<String, Object> reservation = new HashMap<>();
        reservation.put(NAME, RESERVATION_NAME);
        reservation.put(DATE, RESERVATION_DATE);
        reservation.put(TIME_ID, ONE);

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post(RESERVATIONS_API_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.jsonPath().getLong(ID)).isEqualTo(LONG_ONE);
    }

    @Test
    void 에약을_조회한다() {

        //given
        예약을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get(RESERVATIONS_API_URL)
                .then().log().all()
                .extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.jsonPath().getList(WILD_CARD).size()).isEqualTo(ONE);
    }

    @Test
    void 예약을_삭제한다() {

        //given
        예약을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete(RESERVATIONS_API_URL + PATH_VARIABLE_SUFFIX_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
