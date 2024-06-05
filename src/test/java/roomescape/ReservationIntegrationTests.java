package roomescape;

import java.util.List;

import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationIntegrationTests {

	private final TestRestTemplate restTemplate = new TestRestTemplate();

	@LocalServerPort
	private int port;

	@Test
	void reservationControllerEndpoints() {

		// create reservation time
		// given
		ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("10:00");

		// when
		var createReservationTime = this.restTemplate.postForEntity("http://localhost:" + this.port + "/times",
				reservationTimeRequest, ReservationTimeResponse.class);

		// then
		assertThat(createReservationTime.getStatusCode()).isEqualTo(HttpStatus.OK);

		var reservationTimeResponse = createReservationTime.getBody();
		assertThat(reservationTimeResponse).isNotNull();
		assertThat(reservationTimeResponse.startAt()).isEqualTo("10:00");

		// create a reservation
		// given
		ReservationRequest reservationRequest = new ReservationRequest("tester", "2024-06-06", 1L);

		// when
		var createReservation = this.restTemplate.postForEntity("http://localhost:" + this.port + "/reservations",
				reservationRequest, ReservationResponse.class);

		// then
		assertThat(createReservation.getStatusCode()).isEqualTo(HttpStatus.OK);

		ReservationResponse reservationResponse = createReservation.getBody();
		assertThat(reservationResponse).isNotNull();
		assertThat(reservationResponse.name()).isEqualTo("tester");

		// get reservations
		// when
		var getReservations = this.restTemplate.getForEntity("http://localhost:" + this.port + "/reservations",
				List.class);

		// then
		assertThat(getReservations.getStatusCode()).isEqualTo(HttpStatus.OK);
		var reservations = getReservations.getBody();
		assertThat(reservations).isNotNull();
		assertThat(reservations.size()).isGreaterThan(0);

		// cancel reservation
		// given
		Long reservationId = reservationResponse.id();

		// when
		ResponseEntity<Void> cancelResponse = this.restTemplate.exchange(
				"http://localhost:" + this.port + "/reservations/" + reservationId, HttpMethod.DELETE, null,
				Void.class);

		// then
		assertThat(cancelResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// check reservation
		getReservations = this.restTemplate.getForEntity("http://localhost:" + this.port + "/reservations", List.class);

		assertThat(getReservations.getStatusCode()).isEqualTo(HttpStatus.OK);
		reservations = getReservations.getBody();
		assertThat(reservations).isNotNull();
		assertThat(reservations.size()).isEqualTo(0);

		// delete reservation time
		// given
		Long reservationTimeId = reservationTimeResponse.id();

		// when
		var deleteReservationTime = this.restTemplate.exchange(
				"http://localhost:" + this.port + "/times/" + reservationTimeId, HttpMethod.DELETE, null, Void.class);

		// then
		assertThat(deleteReservationTime.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	void reservationTimeControllerEndpoints() {
		// create reservation time
		// given
		ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("10:10");

		// when
		ResponseEntity<ReservationTimeResponse> createReservationTime = this.restTemplate.postForEntity(
				"http://localhost:" + this.port + "/times", reservationTimeRequest, ReservationTimeResponse.class);

		// then
		assertThat(createReservationTime.getStatusCode()).isEqualTo(HttpStatus.OK);
		ReservationTimeResponse reservationTimeResponse = createReservationTime.getBody();
		assertThat(reservationTimeResponse).isNotNull();
		assertThat(reservationTimeResponse.startAt()).isEqualTo("10:10");

		// get reservation times
		// when
		var getReservationTimes = this.restTemplate.getForEntity("http://localhost:" + this.port + "/times",
				List.class);

		// then
		assertThat(getReservationTimes.getStatusCode()).isEqualTo(HttpStatus.OK);
		var reservationTimes = getReservationTimes.getBody();
		assertThat(reservationTimes).isNotNull();
		assertThat(reservationTimes.size()).isGreaterThan(0);

		// delete reservation time
		// given
		Long reservationTimeId = reservationTimeResponse.id();

		// when
		var deleteReservationTime = this.restTemplate.exchange(
				"http://localhost:" + this.port + "/times/" + reservationTimeId, HttpMethod.DELETE, null, Void.class);

		// then
		assertThat(deleteReservationTime.getStatusCode()).isEqualTo(HttpStatus.OK);

		// check reservation time
		getReservationTimes = this.restTemplate.getForEntity("http://localhost:" + this.port + "/times", List.class);

		// then
		assertThat(getReservationTimes.getStatusCode()).isEqualTo(HttpStatus.OK);
		reservationTimes = getReservationTimes.getBody();
		assertThat(reservationTimes).isNotNull();
		assertThat(reservationTimes.size()).isLessThan(1);

	}

}
