package roomescape.presentation.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminPageControllerTest {

    @Test
    void indexPage() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/")
                .then().log().all().extract();
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void reservationPage() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/admin/reservation")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void timePage() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/admin/time")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void themePage() {
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .when().get("/admin/theme")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
