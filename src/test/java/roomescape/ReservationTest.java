package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.DTO.ReservationRequest;
import roomescape.DTO.ReservationResponse;
import roomescape.DTO.ReservationTimeRequest;
import roomescape.DTO.ReservationTimeResponse;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {
    @Test
    @DisplayName("ReservationController - create()")
    void 예약() {
        String name = "yeeun";
        String date = "2024-12-13";
        new ReservationTimeTest().예약_시간_생성();
        new ThemeTest().테마_생성();

        var response = RestAssured
                .given().log().all()
                .body(new ReservationRequest(name, date, 1L, 1L))
                .contentType(ContentType.JSON)
                .when().post("/reservations")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        ReservationResponse body = response.body().as(ReservationResponse.class);
        assertThat(body.getName()).isEqualTo(name);
        assertThat(body.getDate()).isEqualTo(date);
    }

    @Test
    @DisplayName("ReservationController - read()")
    void 젼체_예약_조회() {
        예약();

        var response = RestAssured
                .given().log().all()
                .when().get("/reservations")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList("", ReservationResponse.class)).hasSize(1);
    }

    @Test
    @DisplayName("ReservationController - delete() 1")
    void 예약_취소() {
        예약();

        var response = RestAssured
                .given().log().all()
                .when().delete("/reservations/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("ReservationController - delete() 2")
    void 존재하지_않는_예약_취소() {
        var response = RestAssured
                .given().log().all()
                .when().delete("/reservations/1")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
