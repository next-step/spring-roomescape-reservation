package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.DTO.ReservationTimeRequest;
import roomescape.DTO.ReservationTimeResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeTest {
    @Test
    @DisplayName("ReservationTimeController - create()")
    void 예약_시간_생성() {
        String startAt = "13:00";
        var response = RestAssured
                .given().log().all()
                .body(new ReservationTimeRequest(startAt))
                .contentType(ContentType.JSON)
                .when().post("/times")
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
                .body(new ReservationTimeRequest(startAt))
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @DisplayName("ReservationTimeController - create() : invalid time")
    @ValueSource(strings = {"asdf", "24:24", "", "24:00"})
    void 유효하지_않은_예약_시간_생성(String startAt) {
        var response = RestAssured
                .given().log().all()
                .body(new ReservationTimeRequest(startAt))
                .contentType(ContentType.JSON)
                .when().post("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("ReservationTimeController - read() : all")
    void 전체_예약_시간_조회() {
        예약_시간_생성();

        var response = RestAssured
                .given().log().all()
                .when().get("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ReservationTimeResponse.class)).hasSize(1);
    }

    @Test
    @DisplayName("ReservationTimeController - read() : no created reservation time")
    void 등록된_예약_시간_없는_경우__예약_시간_조회() {
        var response = RestAssured
                .given().log().all()
                .when().get("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("ReservationTimeController - delete()")
    void 예약_시간_삭제() {
        예약_시간_생성();

        var response = RestAssured
                .given().log().all()
                .when().delete("/times/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("ReservationTimeController - delete() : non existent time")
    void 존재하지_않는_예약_시간_삭제() {
        var response = RestAssured
                .given().log().all()
                .when().delete("/times/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("ReservationTimeController - delete() : time with reservation existing")
    void 예약_존재하는_시간_삭제() {
        new ReservationTest().예약();
        var response = RestAssured
                .given().log().all()
                .when().delete("/times/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}