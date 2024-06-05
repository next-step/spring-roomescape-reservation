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
		ReservationTimeRequest timeRequest = new ReservationTimeRequest("10:00");

		// when
		ResponseEntity<ReservationTimeResponse> createTimeResponse = this.restTemplate
			.postForEntity("http://localhost:" + this.port + "/times", timeRequest, ReservationTimeResponse.class);

		// then
		assertThat(createTimeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		ReservationTimeResponse reservationTimeResponse = createTimeResponse.getBody();
		assertThat(reservationTimeResponse).isNotNull();
		assertThat(reservationTimeResponse.startAt()).isEqualTo("10:00");

		// create a reservation
		// given
		ReservationRequest reservationRequest = new ReservationRequest("tester", "2024-06-06", 1L);

		// when
		ResponseEntity<ReservationResponse> createResponse = this.restTemplate.postForEntity(
				"http://localhost:" + this.port + "/reservations", reservationRequest, ReservationResponse.class);

		// then
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		ReservationResponse reservationResponse = createResponse.getBody();
		assertThat(reservationResponse).isNotNull();
		assertThat(reservationResponse.name()).isEqualTo("tester");

		// get reservations
		// when
		ResponseEntity<List> getReservationsResponse = this.restTemplate
			.getForEntity("http://localhost:" + this.port + "/reservations", List.class);

		// then
		assertThat(getReservationsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<ReservationResponse> reservations = getReservationsResponse.getBody();
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
		getReservationsResponse = this.restTemplate
				.getForEntity("http://localhost:" + this.port + "/reservations", List.class);

		assertThat(getReservationsResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		reservations = getReservationsResponse.getBody();
		assertThat(reservations).isNotNull();
		assertThat(reservations.size()).isEqualTo(0);

		// delete reservation time
		// given
		Long reservationTimeId = reservationTimeResponse.id();

		// when
		ResponseEntity<Void> deleteResponse = this.restTemplate.exchange(
				"http://localhost:" + this.port + "/times/" + reservationTimeId, HttpMethod.DELETE, null, Void.class);

		// then
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	void reservationTimeControllerEndpoints() {
		// create reservation time
		// given
		ReservationTimeRequest reservationTimeRequest = new ReservationTimeRequest("10:10");

		// when
		ResponseEntity<ReservationTimeResponse> createResponse = this.restTemplate.postForEntity(
				"http://localhost:" + this.port + "/times", reservationTimeRequest, ReservationTimeResponse.class);

		// then
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		ReservationTimeResponse reservationTimeResponse = createResponse.getBody();
		assertThat(reservationTimeResponse).isNotNull();
		assertThat(reservationTimeResponse.startAt()).isEqualTo("10:10");

		// get reservation times
		// when
		ResponseEntity<List> getReservationTimesResponse = this.restTemplate
			.getForEntity("http://localhost:" + this.port + "/times", List.class);

		// then
		assertThat(getReservationTimesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		List<ReservationTimeResponse> reservationTimes = getReservationTimesResponse.getBody();
		assertThat(reservationTimes).isNotNull();
		assertThat(reservationTimes.size()).isGreaterThan(0);

		// delete reservation time
		// given
		Long reservationTimeId = reservationTimeResponse.id();

		// when
		ResponseEntity<Void> deleteResponse = this.restTemplate.exchange(
				"http://localhost:" + this.port + "/times/" + reservationTimeId, HttpMethod.DELETE, null, Void.class);

		// then
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// check reservation time
		getReservationTimesResponse = this.restTemplate
				.getForEntity("http://localhost:" + this.port + "/times", List.class);

		// then
		assertThat(getReservationTimesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		reservationTimes = getReservationTimesResponse.getBody();
		assertThat(reservationTimes).isNotNull();
		assertThat(reservationTimes.size()).isLessThan(1);

	}

}
