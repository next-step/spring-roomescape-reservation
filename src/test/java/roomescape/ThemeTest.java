package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.theme.domain.Theme;
import roomescape.theme.error.exception.ErrorCode;
import roomescape.theme.error.exception.ThemeException;
import roomescape.theme.presentation.dto.ThemeRequest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ThemeTest {

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String DESCRIPTION = "description";
    private static final String THUMBNAIL = "thumbnail";
    private static final String RIGHT_NAME = "TwentyLengthsExample";
    private static final String RIGHT_DESCRIPTION = "RightDescription";
    private static final String RIGHT_THUMBNAIL_URL = "https://edu.nextstep.camp/spring";

    @Test
    void 테마_관리_페이지를_랜더링한다() {

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/admin/theme")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }

    @Test
    void 테마를_추가한다() {

        //given
        Map<String, Object> theme = new HashMap<>();
        theme.put(NAME, "레벨2 탈출 희망");
        theme.put(DESCRIPTION, "우테코 레벨2를 탈출하는 내용입니다");
        theme.put(THUMBNAIL, "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(theme)
                .when().post("/themes")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getLong(ID)).isEqualTo(1);
    }


    @Test
    void 테마를_조회한다() {

        //given
        테마를_추가한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().get("/themes")
                .then().log().all()
                .extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("$").size()).isEqualTo(1);
    }

    @Test
    void 테마를_삭제한다() {

        //given
        테마를_추가한다();

        //when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .when().delete("/themes/1")
                .then().log().all()
                .extract();

        //then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "안녕", "특수_문자"})
    void 테마_생성_중에_이름의_형식이_맞지_않는_경우_예외를_발생시킨다(String wrongNameExample) {

        //given
        ThemeRequest themeRequest = new ThemeRequest(wrongNameExample, RIGHT_DESCRIPTION, RIGHT_THUMBNAIL_URL);

        //when, then
        assertThatThrownBy(() -> new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDescription(),
                themeRequest.getThumbnail())
        ).isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.INVALID_THEME_NAME_FORMAT_ERROR.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"HelloWorld", "안녕하세요헬로우월드입니다", "HelloWorld안녕"})
    void 테마_생성_중에_이름의_형식이_맞는_경우_예외를_발생하지_않는다(String rightNameExample) {

        //given
        ThemeRequest themeRequest = new ThemeRequest(rightNameExample, RIGHT_DESCRIPTION, RIGHT_THUMBNAIL_URL);

        //when. then
        assertThatCode(() -> new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDescription(),
                themeRequest.getThumbnail())
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "*Hello@123*", "overTwoHundreadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"})
    void 테마_생성_중에_설명의_형식이_맞지_않는_경우_예외를_발생시킨다(String wrongDescriptionExample) {

        //given
        ThemeRequest themeRequest = new ThemeRequest(RIGHT_NAME, wrongDescriptionExample, RIGHT_THUMBNAIL_URL);

        //when, then
        assertThatThrownBy(() -> new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDescription(),
                themeRequest.getThumbnail())
        ).isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.INVALID_THEME_DESCRIPTION_FORMAT_ERROR.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"안녕하세요", "helloWorld", "HelloWorld123", "안녕하세요123", "가", "a", "twoHundreadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"})
    void 테마_생성_중에_설명의_형식이_맞는_경우_예외를_발생하지_않는다(String rightDescriptionExample) {

        //given
        ThemeRequest themeRequest = new ThemeRequest(RIGHT_NAME, rightDescriptionExample, RIGHT_THUMBNAIL_URL);

        //when. then
        assertThatCode(() -> new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDescription(),
                themeRequest.getThumbnail())
        ).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://example.com/invalid path", "ftp://example.com"})
    void 테마_생성_중에_썸네일의_형식이_맞지_않는_경우_예외를_발생시킨다(String wrongThumbnailExample) {

        //given
        ThemeRequest themeRequest = new ThemeRequest(RIGHT_NAME, RIGHT_DESCRIPTION, wrongThumbnailExample);

        //when, then
        assertThatThrownBy(() -> new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDescription(),
                themeRequest.getThumbnail())
        ).isInstanceOf(ThemeException.class)
                .hasMessage(ErrorCode.INVALID_THEME_THUMBNAIL_FORMAT_ERROR.getErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"http://example.com", "https://example.com:8080/path/to/resource", "example.com", "http://localhost:3000", "http://192.168.1.1"})
    void 테마_생성_중에_썸네일의_형식이_맞는_경우_예외를_발생하지_않는다(String rightThumbnailExample) {

        //given
        ThemeRequest themeRequest = new ThemeRequest(RIGHT_NAME, RIGHT_DESCRIPTION, rightThumbnailExample);

        //when. then
        assertThatCode(() -> new Theme(
                null,
                themeRequest.getName(),
                themeRequest.getDescription(),
                themeRequest.getThumbnail())
        ).doesNotThrowAnyException();
    }
}
