package roomescape.domain.reservationtime.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import roomescape.support.RestAssuredTestSupport;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

class ReservationTimeApiTest extends RestAssuredTestSupport {

    @Test
    void reservationTime() {
        Map<String, String> timeAppendHttpRequest = new HashMap<>();
        timeAppendHttpRequest.put("startAt", "10:00");

        // 예약 시간 추가
        final ValidatableResponse response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeAppendHttpRequest)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body(
                        "startAt", is("10:00")
                );

        final Long timeId = ((Integer) response.extract().path("id")).longValue();

        // 예약 시간 전체 조회
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        // 예약 시간 삭제
        RestAssured.given().log().all()
                .when()
                .pathParam("timeId", timeId)
                .delete("/times/{timeId}")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}