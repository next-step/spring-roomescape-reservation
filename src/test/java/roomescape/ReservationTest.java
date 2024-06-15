package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.error.exception.ErrorCode;
import roomescape.reservation.error.exception.ReservationException;
import roomescape.reservation.presentation.dto.ReservationRequest;
import roomescape.theme.domain.Theme;
import roomescape.time.domain.Time;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String DATE = "date";
    private static final String TIME_ID = "timeId";
    private static final String THEME_ID = "themeId";
    private static final String START_AT = "startAt";
    private static final String DESCRIPTION = "description";
    private static final String THUMBNAIL = "thumbnail";

    private Theme theme = null;
    private Time time = null;

    @BeforeEach
    void 시간_예약을_추가한다() {

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
        assertThat(response.jsonPath().getLong(ID)).isEqualTo(1);
        this.time = new Time(
                response.jsonPath().getLong(ID),
                response.jsonPath().getString(START_AT)
        );
    }

    @BeforeEach
    void 테마를_추가한다() {

        //given
        Map<String, Object> theme = new HashMap<>();
        theme.put(NAME, "무시무시한 공포 테마");
        theme.put(DESCRIPTION, "오싹");
        theme.put(THUMBNAIL, "www.youtube.com/boorownie");

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong(ID)).isEqualTo(1L);
        this.theme = new Theme(
                response.jsonPath().getLong(ID),
                response.jsonPath().getString(NAME),
                response.jsonPath().getString(DESCRIPTION),
                response.jsonPath().getString(THUMBNAIL)
        );
    }

    @Test
    void 예약_관리_페이지를_랜더링한다() {

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void 예약을_등록한다() {

        //given
        Map<String, Object> reservation = new HashMap<>();
        reservation.put(NAME, "johnPark");
        reservation.put(DATE, "2023-08-05");
        reservation.put(TIME_ID, 1L);
        reservation.put(THEME_ID, 1L);


        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong(ID)).isEqualTo(1L);
    }

    @Test
    void 에약을_조회한다() {

        //given
        예약을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("$").size()).isEqualTo(1);
    }

    @Test
    void 예약을_삭제한다() {

        //given
        예약을_등록한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"가", "a", "가나다라마바사아자차카타파하거너더러머버서"})
    void 예약_생성_중에_이름의_형식이_맞지_않는_경우_예외를_발생시킨다(String wrongNameExample) {

        //given
        ReservationRequest reservationRequest = new ReservationRequest(wrongNameExample, "2023-08-05", this.time.getId(), theme.getId());

        //when, then
        assertThatThrownBy(() -> new Reservation(
                null, reservationRequest.getName(),
                reservationRequest.getDate(),
                this.time,
                this.theme)
        ).isInstanceOf(ReservationException.class)
                .hasMessage(ErrorCode.INVALID_THEME_NAME_FORMAT_ERROR.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"박민욱", "브라운", "uzbeksAliddin Buriev"})
    void 예약_생성_중에_이름의_형식이_맞는_경우_예외를_발생하지_않는다(String rightNameExample) {

        //given
        ReservationRequest reservationRequest = new ReservationRequest(rightNameExample, "2023-08-05", this.time.getId(), theme.getId());

        //when, then
        assertThatCode(() -> new Reservation(
                null,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                this.time, this.theme)
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024.06.12", "2023-02-29", "2021-04-31", "2100-01-01", "1899-12-31"})
    void 예약_생성_중에_날짜의_형식이_맞지_않는_경우_예외를_발생시킨다(String wrongDateExample) {

        //given
        ReservationRequest reservationRequest = new ReservationRequest("johnPark", wrongDateExample, this.time.getId(), theme.getId());

        //when, then
        assertThatThrownBy(() -> new Reservation(
                null,
                reservationRequest.getName(),
                reservationRequest.getDate(),
                this.time, this.theme)
        ).isInstanceOf(ReservationException.class)
                .hasMessage(ErrorCode.INVALID_THEME_DATE_FORMAT_ERROR.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-06-12", "2024-02-29", "1999-12-31"})
    void 예약_생성_중에_날짜의_형식이_맞는_경우_예외를_발생하지_않는다(String rightDateExample) {

        //given
        ReservationRequest reservationRequest = new ReservationRequest("johnPark", rightDateExample, this.time.getId(), theme.getId());

        //when, then
        assertThatCode(() -> new Reservation(
                null, reservationRequest.getName(),
                reservationRequest.getDate(),
                this.time,
                this.theme)
        ).doesNotThrowAnyException();
    }
}
