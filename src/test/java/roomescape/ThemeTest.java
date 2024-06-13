package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.DTO.ThemeRequest;
import roomescape.DTO.ThemeResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ThemeTest {
    @Test
    @DisplayName("ThemeController - create()")
    void 테마_생성() {
        String name = "수키도키";
        String description = "흐르는 대로 살자 해파리처럼🪼";
        String thumbnail = "https://pbs.twimg.com/media/GApx6fjagAAkFsX.jpg";

        var response = RestAssured
                .given().log().all()
                .body(new ThemeRequest(name, description, thumbnail))
                .contentType(ContentType.JSON)
                .when().post("/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("ThemeController - read()")
    void 전체_테마_조회() {
        테마_생성();

        var response = RestAssured
                .given().log().all()
                .when().get("/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ThemeResponse.class)).hasSize(1);
    }

    @Test
    @DisplayName("ThemeController - delete() 1")
    void 테마_삭제() {
        테마_생성();

        var response = RestAssured
                .given().log().all()
                .when().delete("/themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("ThemeController - delete() 2")
    void 존재하지_않는_테마_삭제() {
        var response = RestAssured
                .given().log().all()
                .when().delete("/themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
