package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.ui.dto.ReservationRequest;
import roomescape.reservationtime.ui.dto.ReservationTimeRequest;
import roomescape.reservationtime.ui.dto.ReservationTimeResponse;
import roomescape.theme.ui.dto.ThemeRequest;
import roomescape.reservation.application.ReservationService;
import roomescape.reservationtime.application.ReservationTimeService;
import roomescape.theme.application.ThemeService;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=8888"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeTest {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationTimeService reservationTimeService;
    @Autowired
    private ThemeService themeService;
    private final String URL = "http://localhost:8888";

    private long makeDummyReservation() {
        long timeId = reservationTimeService.add(ReservationTimeRequest.create("13:00"));
        long themeId = themeService.add(ThemeRequest.create("a", "b", "c"));
        String date = LocalDate.now().plusWeeks(1).toString();
        return reservationService.make(ReservationRequest.create("yeeun", date, timeId, themeId));
    }

    @Test
    @DisplayName("ReservationTimeController - create()")
    void 예약_시간_생성() {
        String startAt = "13:00";
        var response = RestAssured
                .given().log().all()
                .body(ReservationTimeRequest.create(startAt))
                .contentType(ContentType.JSON)
                .when().post(URL + "/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        ReservationTimeResponse body = response.body().as(ReservationTimeResponse.class);
        assertThat(body.getStartAt()).isEqualTo(startAt);
    }

    @Test
    @DisplayName("ReservationTimeController - create() : duplicated start time")
    void 중복_예약_시간_생성() {
        예약_시간_생성();
        String startAt = "13:00";
        var response = RestAssured
                .given().log().all()
                .body(ReservationTimeRequest.create(startAt))
                .contentType(ContentType.JSON)
                .when().post(URL + "/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @DisplayName("ReservationTimeController - create() : invalid time")
    @ValueSource(strings = {"asdf", "24:24", "", "24:00"})
    void 유효하지_않은_예약_시간_생성(String startAt) {
        var response = RestAssured
                .given().log().all()
                .body(ReservationTimeRequest.create(startAt))
                .contentType(ContentType.JSON)
                .when().post(URL + "/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("ReservationTimeController - read() : all")
    void 전체_예약_시간_조회() {
        예약_시간_생성();

        var response = RestAssured
                .given().log().all()
                .when().get(URL + "/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ReservationTimeResponse.class)).hasSize(1);
    }

    @Test
    @DisplayName("ReservationTimeController - read() : no created reservation time")
    void 등록된_예약_시간_없는_경우__예약_시간_조회() {
        var response = RestAssured
                .given().log().all()
                .when().get(URL + "/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ReservationTimeResponse.class)).hasSize(0);
    }

    @Test
    @DisplayName("ReservationTimeController - delete()")
    void 예약_시간_삭제() {
        예약_시간_생성();

        var response = RestAssured
                .given().log().all()
                .when().delete(URL + "/times/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("ReservationTimeController - delete() : non existent time")
    void 존재하지_않는_예약_시간_삭제() {
        var response = RestAssured
                .given().log().all()
                .when().delete(URL + "/times/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("ReservationTimeController - delete() : time with reservation existing")
    void 예약_존재하는_시간_삭제() {
        long reservationId = makeDummyReservation();
        var response = RestAssured
                .given().log().all()
                .when().delete(URL + "/times/" + reservationId)
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
