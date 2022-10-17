package nextstep.controller;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import nextstep.dto.ErrorResponse;
import nextstep.dto.reservation.ReservationCreateRequest;
import nextstep.dto.reservation.ReservationFindAllResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static nextstep.Constants.*;
import static nextstep.service.ReservationService.*;
import static org.assertj.core.api.Assertions.assertThat;

class ReservationAcceptanceTest extends AcceptanceTest {
    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        initScheduleTable();
        initReservationTable();
        스케줄생성();
    }

    @Test
    @DisplayName("POST 예약 생성")
    void createReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);

        // when
        ExtractableResponse<Response> response = createReservation(request);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("POST 예약 생성 - 존재하지 않는 스케줄에 예약 시, 예약 실패")
    void noSuchSchedule() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(2L, NAME);

        // when
        ExtractableResponse<Response> response = createReservation(request);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(NO_SUCH_SCHEDULE_MESSAGE);
    }

    @Test
    @DisplayName("POST 예약 생성 - 중복 예약 시, 예약 실패")
    void failToCreate() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);
        createReservation(request);

        // when
        ExtractableResponse<Response> response = createReservation(request);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(DUPLICATE_RESERVATION_MESSAGE);
    }

    @Test
    @DisplayName("GET 예약 전체조회")
    void findAllReservations() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);
        createReservation(request);

        // when
        ExtractableResponse<Response> response = findAllReservations(DATE_STRING);
        ReservationFindAllResponse reservationFindAllResponse = response.as(ReservationFindAllResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(reservationFindAllResponse.getReservations()).hasSize(1);
    }

    @Test
    @DisplayName("DELETE 예약 삭제")
    void deleteReservation() {
        // given
        ReservationCreateRequest request = new ReservationCreateRequest(SCHEDULE_ID, NAME);
        createReservation(request);

        // when
        ExtractableResponse<Response> response = deleteReservation(DATE_STRING, TIME_STRING);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("DELETE 예약 삭제 - 예약이 없을 경우, 삭제 불가")
    void failToDelete() {
        // given, when
        ExtractableResponse<Response> response = deleteReservation(DATE_STRING, TIME_STRING);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(errorResponse.getMessage()).isEqualTo(NO_RESERVATION_MESSAGE);
    }

    private ExtractableResponse<Response> createReservation(ReservationCreateRequest request) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .when().post("/reservations")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> findAllReservations(String date) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("date", date)
                .when().get("/reservations")
                .then().log().all().extract();
    }

    private ExtractableResponse<Response> deleteReservation(String date, String time) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("date", date)
                .queryParam("time", time)
                .when().delete("/reservations")
                .then().log().all().extract();
    }
}
