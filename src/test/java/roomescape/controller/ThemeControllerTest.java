package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.theme.create.ThemeCreateRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ThemeControllerTest {

    @Autowired
    ThemeController themeController;


    @BeforeEach
    void init() {
        themeController.createTheme(new ThemeCreateRequest(
                "엄청 무서운 이야기", "설명은 없습니다.", "https://gg")
        );
    }

    @Test
    void findThemes() {
         var response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }


    @Test
    void create() {
        var response = RestAssured
                .given().log().all()
                .body(new ThemeCreateRequest("엄청 무서운 이야기", "설명은 없습니다.", "https://gg"))
                .contentType(ContentType.JSON)
                .when().post("/themes")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void delete() {
        //init 메서드에 theme 추가
        var response = RestAssured
                .given().log().all()
//                .body()
                .contentType(ContentType.JSON)
                .when().delete("themes/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}