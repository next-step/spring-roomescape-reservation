package roomescape.admin.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import roomescape.admin.dto.SaveThemeRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ThemeControllerTest {

    @Nested
    @DisplayName("테마는")
    class Describe_Theme{

        void given_테마(){
            given_테마_삭제();
            SaveThemeRequest 테마 = new SaveThemeRequest("테마","테마 설명","썸네일.png");

            RestAssured.given().log().all()
                    .body(테마)
                    .contentType(ContentType.JSON)
                    .when().post("/themes");
        }

        void given_테마_삭제(){
            RestAssured.given().log().all()
                    .when().delete("/themes/1");
        }

        @Nested
        @DisplayName("데이터 유효성 검증을 진행할 때")
        class Context_with_validate_data{

            @Test
            @DisplayName("name에 문자 이외의 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_non_string_in_name(){
                SaveThemeRequest 테마_이름_52 = new SaveThemeRequest("테마52","테마 설명","썸네일.png");

                var response = RestAssured.given().log().all()
                        .body(테마_이름_52)
                        .contentType(ContentType.JSON)
                        .when().post("/themes")
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            }

            @Test
            @DisplayName("name에 null, (공백) 값이 입력되면, 에러를 리턴합니다.")
            void it_return_error_for_null_or_empty_name(){
                SaveThemeRequest 테마_이름_empty = new SaveThemeRequest("","테마 설명","썸네일.png");

                var response = RestAssured.given().log().all()
                        .body(테마_이름_empty)
                        .contentType(ContentType.JSON)
                        .when().post("/themes")
                        .then().log().all().extract();

                assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            }

        }

        @Test
        @DisplayName("입력한 뒤, 저장한 Dto를 리턴합니다.")
        void it_return_theme_dto_after_saving_theme(){
            SaveThemeRequest 테마 = new SaveThemeRequest("테마","테마 설명","썸네일.png");

            var response = RestAssured.given().log().all()
                    .body(테마)
                    .contentType(ContentType.JSON)
                    .when().post("/themes")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.jsonPath().getLong("id")).isPositive();
        }

        @Test
        @DisplayName("목록 List를 리턴합니다.")
        void it_return_list_theme(){
            given_테마();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().get("/themes")
                    .then().log().all().extract();

            var themes = response.as(List.class);

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
            assertThat(themes).hasSize(1);
        }

        @Test
        @DisplayName("삭제하고, void를 리턴합니다.")
        void it_return_void_and_delete_theme(){
            given_테마();

            var response = RestAssured
                    .given().log().all()
                    .contentType(ContentType.JSON)
                    .when().delete("/themes/1")
                    .then().log().all().extract();

            assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        }

    }

}