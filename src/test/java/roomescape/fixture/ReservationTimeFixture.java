package roomescape.fixture;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;

public class ReservationTimeFixture {

    public static void 예약시간을_생성한다(Map<String, String> params) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }
}
