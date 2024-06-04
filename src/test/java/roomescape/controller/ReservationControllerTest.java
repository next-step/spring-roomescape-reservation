package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {

    @Test
    void readReservation() {
        var response = RestAssured
                .given().log().all()
                .when().get("/reservations")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList(".", ReservationDto.class)).hasSize(2);
    }

    @Test
    void createReservation() {
        // given
        final ReservationDto request = new ReservationDto("제이슨", "2023-08-05", "15:40");

        // when
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ReservationDto responseDto = response.as(ReservationDto.class);
        assertThat(responseDto.getName()).isEqualTo(request.getName());
        assertThat(responseDto.getDate()).isEqualTo(request.getDate());
        assertThat(responseDto.getTime()).isEqualTo(request.getTime());
    }
}
