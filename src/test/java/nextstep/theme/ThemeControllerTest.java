package nextstep.theme;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.SpringControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class ThemeControllerTest extends SpringControllerTest {

    @Autowired
    private ThemeJdbcRepository themeJdbcRepository;

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        themeJdbcRepository.clear();
    }

    @DisplayName("테마 생성")
    @Test
    void createTheme() {
        // given
        ThemeCreateRequest request = new ThemeCreateRequest(
                "404호의 비밀",
                "분명 있어야할 곳에 아무것도 없었다.. 남겨진 쪽지엔 404 NOT FOUND 만이 남아있는데...",
                22000
        );

        // when
        ExtractableResponse<Response> response = 테마_생성_요청(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("테마 생성시 이미 존재하는 테마 이름이면, 생성에 실패한다.")
    @Test
    void createThemeDuplicatedThemeName() {
        // given
        ThemeCreateRequest request1 = new ThemeCreateRequest(
                "404호의 비밀",
                "분명 있어야할 곳에 아무것도 없었다.. 남겨진 쪽지엔 404 NOT FOUND 만이 남아있는데...",
                22000
        );
        ExtractableResponse<Response> response1 = 테마_생성_요청(request1);
        assertThat(response1.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        ThemeCreateRequest request2 = new ThemeCreateRequest(
                "404호의 비밀",
                "404호는 존재하지 않아야하는데, 눈을 떠보니 내가 있는곳이 404호였다..",
                22000
        );

        // when
        ExtractableResponse<Response> response2 = 테마_생성_요청(request2);

        // then
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private static ExtractableResponse<Response> 테마_생성_요청(ThemeCreateRequest request) {
        return given()
                .body(request).contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/themes")
                .then().log().all().extract();
    }
}
