package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import roomescape.support.RestAssuredTestSupport;

public class MissionStepTest extends RestAssuredTestSupport {

    @Test
    void page() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }
}
