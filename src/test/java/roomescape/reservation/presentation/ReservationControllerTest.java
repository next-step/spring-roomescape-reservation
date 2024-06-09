package roomescape.reservation.presentation;

import java.time.LocalDate;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import roomescape.reservation.presentation.dto.ReservationCreateRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    private String date = LocalDate.now().plusDays(1).toString();

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
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", date, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .body("size()", Matchers.is(1));
    }

    @Test
    @DisplayName("예약을 생성 시 존재하지 않는 시간 ID로 생성하면 예외가 발생한다.")
    void testCreateReservation_InvalidTimeId() {
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", date, 2L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약을 생성 시 존재하지 않는 테마 ID로 생성하면 예외가 발생한다.")
    void testCreateReservation_InvalidThemeId() {
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", date, 1L, 2L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약을 생성 시 이미 예약된 시간으로 생성하면 예외가 발생한다.")
    void testCreateReservation_AlreadyExist() {
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", date, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약을 생성 시 지난 날짜로 생성하면 예외가 발생한다.")
    void testCreateReservation_PastDate() {
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", "2021-07-01", 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("예약을 생성 시 이름이 없으면 예외가 발생한다.")
    void testCreateReservation_InvalidName(String name) {
        ReservationCreateRequest request = new ReservationCreateRequest(name, date, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("예약을 생성 시 날짜가 없으면 예외가 발생한다.")
    void testCreateReservation_InvalidDate(String date) {
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", date, 1L, 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void testCancelReservation() {
        ReservationCreateRequest request = new ReservationCreateRequest("브라운", date, 1L, 1L);

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
