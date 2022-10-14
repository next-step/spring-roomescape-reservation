package nextstep.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import io.restassured.RestAssured;
import java.math.BigDecimal;
import java.util.List;
import nextstep.ui.request.ThemeCreateRequest;
import nextstep.ui.response.ThemeResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ThemeAcceptanceTest extends AcceptanceTest {

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        jdbcTemplate.execute("DROP TABLE theme IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE theme("
            + "id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
            + "name VARCHAR(100) NOT NULL,"
            + "description TEXT NOT NULL,"
            + "price DECIMAL NOT NULL"
            + ")");
    }

    @DisplayName("테마 생성 - POST /themes")
    @Test
    void create() {
        ThemeCreateRequest request = themeCreateRequest();

        RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/themes")
            .then().log().all()
            .statusCode(CREATED.value());
    }

    @DisplayName("모든 테마 조회 - GET /themes")
    @Test
    void getThemes() {
        테마_생성("비밀의방", "비밀의방이에욥!", BigDecimal.valueOf(50_000));

        List<ThemeResponse> results = RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().get("/themes")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .extract().jsonPath().getList(".", ThemeResponse.class);

        assertThat(results).hasSize(1);
    }

    @DisplayName("테마 삭제 - DELETE /themes/{themeId}")
    @Test
    void deleteById() {
        String themeId = 테마_생성("비밀의방", "비밀의방이에욥!", BigDecimal.valueOf(50_000));

        RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().delete(themeId)
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("테마 삭제시 해당되는 테마가 없으면 삭제를 실패한다. - DELETE /themes/{themeId}")
    @Test
    void deleteByIdException() {
        RestAssured.given().log().all()
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .when().delete("/themes/999")
            .then().log().all()
            .statusCode(BAD_REQUEST.value())
            .body(is("ID 에 해당하는 테마가 없습니다."));
    }

    private String 테마_생성(String name, String desc, BigDecimal price) {
        ThemeCreateRequest request = themeCreateRequest(name, desc, price);

        return RestAssured.given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(request)
            .when().post("/themes")
            .then().log().all()
            .statusCode(CREATED.value())
            .extract().header("Location");
    }

    private ThemeCreateRequest themeCreateRequest() {
        return themeCreateRequest(
            "비밀의방",
            "기가막힌 방이랍니다~",
            BigDecimal.valueOf(50_000)
        );
    }

    private ThemeCreateRequest themeCreateRequest(
        String name,
        String desc,
        BigDecimal price
    ) {
        return new ThemeCreateRequest(name, desc, price);
    }
}
