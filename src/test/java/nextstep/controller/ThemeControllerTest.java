package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ThemeControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("POST 테마 생성")
    void create() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest("어마어마한 테마", "짱무서움", 20_000);

        // when
        ExtractableResponse<Response> response = createTheme(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("GET 테마 전체조회")
    void findAll() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest("어마어마한 테마", "짱무서움", 20_000);
        createTheme(request);

        // when
        ExtractableResponse<Response> response = findAllThemes();
        ThemeFindAllResponse themeFindAllResponse = response.as(ThemeFindAllResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(themeFindAllResponse.getThemes()).hasSize(1);
    }

    private ExtractableResponse<Response> createTheme(ThemeCreateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/themes")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> findAllThemes() {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/themes")
                .then().log().all().extract();
    }
}
