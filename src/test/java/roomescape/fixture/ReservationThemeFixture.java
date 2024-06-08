package roomescape.fixture;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.springframework.http.HttpStatus;

public class ReservationThemeFixture {

    public static void 예약테마를_생성한다(Map<String, String> params) {
        int expectedIdValue = 1;

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/" + expectedIdValue)
                .body("id", is(expectedIdValue));
    }
}
