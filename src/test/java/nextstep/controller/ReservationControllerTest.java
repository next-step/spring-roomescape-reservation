package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ErrorResponse;
import nextstep.dto.ReservationCreateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("POST 예약 생성")
    void createReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest("2022-12-01", "12:01", "조아라");

        // when
        ExtractableResponse<Response> response = createReservation(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("POST 예약 생성 - 중복 예약 시, 예약 실패")
    void failToCreate() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest("2022-12-05", "12:05", "조아라");
        createReservation(request);

        // when
        ExtractableResponse<Response> response = createReservation(request);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo("동시간대에 이미 예약이 존재합니다.");
    }

    private ExtractableResponse<Response> createReservation(ReservationCreateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();
    }
}
