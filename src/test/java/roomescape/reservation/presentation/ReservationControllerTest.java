package roomescape.reservation.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import roomescape.reservation.presentation.dto.ReservationCreateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @BeforeEach
    void setUp() {
        Map<String, String> param = Map.of("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(param)
                .when().post("/times")
                .then().log().all();

        Map<String, String> params = Map.of("name", "레벨1 탈출",
                "description", "우테코 레벨1을 탈출하는 내용입니다.",
                "thumbnail", "https://i.pinimg.com/236x/6e/bc/46/6ebc461a94a49f9ea3b8bbe2204145d4.jpg");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/themes")
                .then().log().all();
    }

    @Test
    @DisplayName("모든 예약을 조회한다.")
    void testGetReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("예약을 생성한다.")
    void testCreateReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", "2021-08-01", 1L, 1L);

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.jsonPath().getLong("reservationId")).isEqualTo(1L);
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void testCancelReservations() {
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", "2021-08-01", 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .extract();

        RestAssured.given().log().all()
                .when().delete("/reservations/{reservationId}", 1L)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
