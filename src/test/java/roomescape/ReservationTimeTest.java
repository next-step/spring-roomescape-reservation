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
public class ReservationTimeTest {

    @Test
    void createTime() {
        Map<String, String> timesParams = new HashMap<>();
        timesParams.put("startAt", "2024-08-05T10:00:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timesParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));
    }
}
