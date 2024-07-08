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
public class ThemeTest {

    @Test
    void createTheme() {
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
    }
}
