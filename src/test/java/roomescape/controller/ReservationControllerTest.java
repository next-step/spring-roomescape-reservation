package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import roomescape.reservation.ReservationRequestDto;
import roomescape.reservation.ReservationResponseDto;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ReservationControllerTest {

    @DisplayName("전체 예약을 조회 합니다.")
    @Test
    void readReservation() {
        var response = RestAssured
                .given().log().all()
                .when().get("/reservations")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.jsonPath().getList(".", ReservationResponseDto.class)).hasSize(2);
    }

    @DisplayName("예약을 생성합니다.")
    @Test
    void createReservation() {
        // given
        final ReservationRequestDto request = new ReservationRequestDto("제이슨", "2023-08-05", "15:40");

        // when
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ReservationResponseDto responseDto = response.as(ReservationResponseDto.class);
        assertThat(responseDto.getName()).isEqualTo(request.getName());
        assertThat(responseDto.getDate()).isEqualTo(request.getDate());
        assertThat(responseDto.getReservationTimeResponseDto().getStartAt()).isEqualTo(request.getReservationTimeRequestDto().getStartAt());
    }

    @DisplayName("예약을 삭제합니다.")
    @Test
    void deleteReservation(){
        // given
        final ReservationRequestDto request = new ReservationRequestDto("메이븐", "2023-08-05", "15:40");

        // when
        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ReservationResponseDto responseDto = response.as(ReservationResponseDto.class);
        assertThat(responseDto.getName()).isEqualTo(request.getName());
        assertThat(responseDto.getDate()).isEqualTo(request.getDate());
        assertThat(responseDto.getReservationTimeResponseDto().getStartAt()).isEqualTo(request.getReservationTimeRequestDto().getStartAt());

        // when
        var response2 = RestAssured.given().log().all()
                .when().delete("/reservations/"+responseDto.getId())
                .then().log().all().extract();

        // then
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
