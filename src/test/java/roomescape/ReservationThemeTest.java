package roomescape;

import static org.hamcrest.Matchers.is;
import static roomescape.fixture.ReservationFixture.예약을_생성한다;
import static roomescape.fixture.ReservationThemeFixture.예약테마를_생성한다;
import static roomescape.fixture.ReservationTimeFixture.예약시간을_생성한다;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("예약테마 테스트")
public class ReservationThemeTest {

    private final String NAME = "레벨2 탈출";
    private final String DESCRIPTION = "우테코 레벨2를 탈출하는 내용입니다.";
    private final String THUMBNAIL = "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg";

    @Test
    @DisplayName("예약테마를 생성한다.")
    void createReservationTheme() {
        Map<String, String> params = new HashMap<>();
        params.put("name", NAME);
        params.put("description", DESCRIPTION);
        params.put("thumbnail", THUMBNAIL);

        Response response = 예약테마를_생성한다(params);

        int expectedIdValue = 1;
        response.then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", "/themes/" + expectedIdValue)
                .body("id", is(expectedIdValue));
    }

    @Test
    @DisplayName("예약테마를 생성할 때 필수값이 없는 경우 에러가 발생한다.")
    void missingRequiredFieldsThrowsErrorOnThemeCreation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "");
        params.put("description", DESCRIPTION);
        params.put("thumbnail", THUMBNAIL);

        Response response = 예약테마를_생성한다(params);

        response.then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약테마를 생성할 때 테마이름이 중복인 경우 에러가 발생한다.")
    void createReservationThemeDuplicate() {
        Map<String, String> params = new HashMap<>();
        params.put("name", NAME);
        params.put("description", DESCRIPTION);
        params.put("thumbnail", THUMBNAIL);

        Response firstCreateResponse = 예약테마를_생성한다(params);

        firstCreateResponse.then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        Response secondCreateResponse = 예약테마를_생성한다(params);

        secondCreateResponse.then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("예약테마 목록을 조회한다.")
    void findAllReservationThemes() {
        Map<String, String> params = new HashMap<>();
        params.put("name", NAME);
        params.put("description", DESCRIPTION);
        params.put("thumbnail", THUMBNAIL);

        예약테마를_생성한다(params);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().get("/themes")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약테마를 삭제한다.")
    void deleteReservationTheme() {
        Map<String, String> params = new HashMap<>();
        params.put("name", NAME);
        params.put("description", DESCRIPTION);
        params.put("thumbnail", THUMBNAIL);

        예약테마를_생성한다(params);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("예약테마를 삭제할 때 사용중인 예약이 있는 경우 에러가 발생한다.")
    void deleteReservationThemeExistReservation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", NAME);
        params.put("description", DESCRIPTION);
        params.put("thumbnail", THUMBNAIL);

        예약테마를_생성한다(params);

        params.clear();
        params.put("startAt", "10:00");

        예약시간을_생성한다(params);

        params.clear();
        params.put("name", "브라운");
        params.put("date", "2024-06-25");
        params.put("timeId", "1");
        params.put("themeId", "1");
        예약을_생성한다(params);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().delete("/themes/1")
                .then().log().all()
                .statusCode(HttpStatus.CONFLICT.value());
    }
}
