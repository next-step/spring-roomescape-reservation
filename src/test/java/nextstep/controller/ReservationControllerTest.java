package nextstep.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import nextstep.domain.Reservation;
import nextstep.dto.ReservationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("날짜와 시간, 이름을 넣어 Reservation 을 저장한다.")
    void createReservations01() {
        ReservationRequest reservationRequest = createRequest(LocalDate.parse("2022-08-11"));

        ExtractableResponse<Response> extract = createReservation(reservationRequest);

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("중복된 날짜와 시간에 예약하면 400 에러가 발생한다.")
    void createReservations02() {
        ReservationRequest reservationRequest = createRequest(LocalDate.parse("2022-08-12"));
        createReservation(reservationRequest);

        ExtractableResponse<Response> extract = createReservation(reservationRequest);

        assertThat(extract.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("날짜를 이용해 Reservations 를 조회한다.")
    void getReservations() {
        String dateString = "2022-08-13";
        ReservationRequest reservationRequest = createRequest(LocalDate.parse(dateString));
        createReservation(reservationRequest);

        List<Reservation> reservations = findReservations(dateString);

        assertThat(reservations).hasSize(1);
    }

    @Test
    @DisplayName("날짜와 시간을 넣어 Reservation 을 삭제한다.")
    void deleteReservations() {
        String dateString = "2022-08-14";
        ReservationRequest reservationRequest = createRequest(LocalDate.parse(dateString));
        createReservation(reservationRequest);

        assertThat(findReservations(dateString)).hasSize(1);
        ExtractableResponse<Response> response = deleteReservation(dateString, "13:00");
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(findReservations(dateString)).isEmpty();
    }

    private ReservationRequest createRequest(LocalDate date) {
        final LocalTime time = LocalTime.parse("13:00");
        final String name = "mungto";
        return new ReservationRequest(date, time, name);
    }

    private ExtractableResponse<Response> createReservation(ReservationRequest reservationRequest) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(reservationRequest)
            .when().post("/reservations")
            .then().log().all()
            .extract();
    }

    private List<Reservation> findReservations(String date) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .param("date", date)
            .when().get("/reservations")
            .then().log().all()
            .extract().body().as(new TypeRef<>() {
            });
    }

    private ExtractableResponse<Response> deleteReservation(String date, String time) {
        return RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .param("date", date)
            .param("time", time)
            .when().delete("/reservations")
            .then().log().all()
            .extract();
    }
}
