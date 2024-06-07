package roomescape;

import static org.hamcrest.Matchers.is;
import static roomescape.fixture.ReservationTimeFixture.예약시간을_생성한다;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("예약시간 테스트")
public class ReservationTimeApiTest {

    @Test
    @DisplayName("예약시간을 생성한다.")
    void createReservationTime() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        예약시간을_생성한다(params);
    }

    @Test
    @DisplayName("예약시간 목록을 조회한다.")
    void findAllReservationTimes() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        예약시간을_생성한다(params);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약시간을 삭제한다.")
    void deleteReservationTime() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        예약시간을_생성한다(params);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}
