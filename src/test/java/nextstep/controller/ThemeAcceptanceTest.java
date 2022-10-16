package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ThemeCreateRequest;
import nextstep.dto.ThemeFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static nextstep.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

class ThemeAcceptanceTest extends AcceptanceTest {
    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        initThemeTable();
    }

    @Test
    @DisplayName("POST 테마 생성")
    void create() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);

        // when
        ExtractableResponse<Response> response = createTheme(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("GET 테마 전체조회")
    void findAll() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        createTheme(request);

        // when
        ExtractableResponse<Response> response = findAllThemes();
        ThemeFindAllResponse themeFindAllResponse = response.as(ThemeFindAllResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(themeFindAllResponse.getThemes()).hasSize(1);
    }

    @Test
    @DisplayName("DELETE 테마 삭제")
    void deleteTheme() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        createTheme(request);

        // when
        ExtractableResponse<Response> response = deleteTheme(1L);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
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

    private ExtractableResponse<Response> deleteTheme(Long id) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/themes/" + id)
                .then().log().all().extract();
    }
}
