package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ErrorResponse;
import nextstep.dto.theme.ThemeCreateRequest;
import nextstep.dto.theme.ThemeFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static nextstep.Constants.*;
import static nextstep.service.ThemeService.*;
import static org.assertj.core.api.Assertions.assertThat;

class ThemeAcceptanceTest extends AcceptanceTest {
    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        initThemeTable();
        initScheduleTable();
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
    @DisplayName("POST 테마 생성 - 같은 이름의 테마 존재 시, 생성 실패")
    void failToCreate() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        createTheme(request);

        // when
        ExtractableResponse<Response> response = createTheme(request);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(DUPLICATE_THEME_MESSAGE);
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
        ExtractableResponse<Response> response = deleteTheme(THEME_ID);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("DELETE 테마 삭제 - 예약 존재 시, 삭제 실패")
    void failToDelete() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(THEME_NAME, THEME_DESC, PRICE);
        createTheme(request);
        스케줄생성();
        예약생성();

        // when
        ExtractableResponse<Response> response = deleteTheme(THEME_ID);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(CANT_DELETE_THEME);
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
