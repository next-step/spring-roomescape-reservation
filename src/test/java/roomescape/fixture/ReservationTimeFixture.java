package roomescape.fixture;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;

public class ReservationTimeFixture {

    public static Response 예약시간을_생성한다(Map<String, String> params) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");
    }
}
