package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionStepTest {

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void page() {
        RestAssured.given().log().all()
            .when().get("/")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    void reservation() {
        Map<String, Object> reservationTimeRequest = reservationTimeRequest();
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(reservationTimeRequest)
            .when().post("/times")
            .then()
            .log().all()
            .statusCode(200)
            .body("id", is(1));

        Map<String, Object> reservationRequest = reservationRequest();
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(reservationRequest)
            .when().post("/reservations")
            .then()
            .log().all()
            .statusCode(200)
            .body("id", is(1));

        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1));

        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(200);

        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(0));
    }

    private Map<String, Object> reservationRequest() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", 1);
        return params;
    }

    private Map<String, Object> reservationTimeRequest() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", 1);
        params.put("startAt", "10:00");
        return params;
    }
}
