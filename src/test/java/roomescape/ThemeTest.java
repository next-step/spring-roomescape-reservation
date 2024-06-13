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
        ThemeResponse body = response.body().as(ThemeResponse.class);
        assertThat(body.getName()).isEqualTo(name);
        assertThat(body.getDescription()).isEqualTo(description);
        assertThat(body.getThumbnail()).isEqualTo(thumbnail);
    }

    @Test
    @DisplayName("ThemeController - create() : duplicated name")
    void 중복_이름_테마_생성() {
        테마_생성();
        String name = "수키도키";
        String description = "흐르는 대로 살자 해파리처럼🪼";
        String thumbnail = "https://pbs.twimg.com/media/GApx6fjagAAkFsX.jpg";

        var response = RestAssured
                .given().log().all()
                .body(new ThemeRequest(name, description, thumbnail))
                .contentType(ContentType.JSON)
                .when().post("/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("ThemeController - read() : all")
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
    @DisplayName("ThemeController - delete()")
    void 테마_삭제() {
        테마_생성();

        var response = RestAssured
                .given().log().all()
                .when().delete("/themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("ThemeController - delete() : non existent theme")
    void 존재하지_않는_테마_삭제() {
        var response = RestAssured
                .given().log().all()
                .when().delete("/themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("ThemeController - delete() : theme with reservation existing")
    void 예약_존재하는_테마_삭제() {
        new ReservationTest().예약();

        var response = RestAssured
                .given().log().all()
                .when().delete("/themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }
}
