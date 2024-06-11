package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ThemeTest {

    private static final String RENDERING_THEME_URL = "/admin/theme";
    private static final String THEMES_API_URL = "/themes";
    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String THEME_NAME = "레벨2 탈출";
    private static final String DESCRIPTION = "description";
    private static final String THEME_DESCRIPTION = "우테코 레벨2를 탈출하는 내용입니다.";
    private static final String THUMBNAIL = "thumbnail";
    private static final String THEME_THUMNAIL_URL = "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg";
    private static final int ONE = 1;
    private static final String WILD_CARD = "$";

    @Test
    void 테마_관리_페이지를_랜더링한다() {

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get(RENDERING_THEME_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void 테마를_추가한다() {

        //given
        Map<String, Object> theme = new HashMap<>();
        theme.put(NAME, THEME_NAME);
        theme.put(DESCRIPTION, THEME_DESCRIPTION);
        theme.put(THUMBNAIL, THEME_THUMNAIL_URL);


        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(theme)
                .when().post(THEMES_API_URL)
                .then().log().all()
                .extract();

        //then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.jsonPath().getLong(ID)).isEqualTo(ONE);
    }


    @Test
    void 테마를_조회한다() {

        //given
        테마를_추가한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get(THEMES_API_URL)
                .then().log().all()
                .extract();

        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(response.jsonPath().getList(WILD_CARD).size()).isEqualTo(ONE);
    }
}
