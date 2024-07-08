package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {

    @Test
    void createAndDeleteReservation() {
        // Create theme
        Map<String, String> themeParams = new HashMap<>();
        themeParams.put("name", "테마1");
        themeParams.put("description", "테마 설명");
        themeParams.put("thumbnail", "https://wamel.site");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(themeParams)
                .when().post("/themes")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        // Create time
        Map<String, String> timesParams = new HashMap<>();
        timesParams.put("startAt", "2024-08-05T10:00:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timesParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        // Create reservation
        Map<String, String> reservationsParams = new HashMap<>();
        reservationsParams.put("name", "브라운");
        reservationsParams.put("date", "2024-08-05");
        reservationsParams.put("timeId", "1");
        reservationsParams.put("themeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationsParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        // Verify reservation
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        // Delete reservation
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        // Verify deletion
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}
