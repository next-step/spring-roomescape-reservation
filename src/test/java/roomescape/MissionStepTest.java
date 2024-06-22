package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.data.ReservationAddRequestDto;
import roomescape.reservationtime.data.ReservationTimeAddRequestDto;
import roomescape.theme.data.ThemeAddRequestDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionStepTest {

    @Test
    void page() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void reservation() {
        ReservationAddRequestDto reservationAddRequestDto = new ReservationAddRequestDto(
          "브라운",
          LocalDate.now().plusDays(1).toString(),
          1L,
          1L
        );

        ReservationTimeAddRequestDto reservationTimeAddRequest =
          new ReservationTimeAddRequestDto("15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTimeAddRequest)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        ThemeAddRequestDto themeAddRequestDto = new ThemeAddRequestDto(
          "테마1",
          "테마1 설명",
          "테마1 썸네일"
        );

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
              .body(themeAddRequestDto)
                .when().post("/themes")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationAddRequestDto)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

}
