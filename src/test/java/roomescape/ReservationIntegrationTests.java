package roomescape;

import java.util.List;

import org.junit.jupiter.api.Test;
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.controller.dto.ThemeRequest;
import roomescape.controller.dto.ThemeResponse;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
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

		// create theme
		// given
		ThemeRequest themeRequest = new ThemeRequest("테마1", "첫번째테마", "테마이미지");

		// when
		var createTheme = this.restTemplate.postForEntity("http://localhost:" + this.port + "/themes", themeRequest,
				ThemeResponse.class);

		// then
		assertThat(createTheme.getStatusCode()).isEqualTo(HttpStatus.OK);

		var themeResponse = createTheme.getBody();
		assertThat(themeResponse).isNotNull();
		assertThat(themeResponse.id()).isEqualTo(1L);
		assertThat(themeResponse.name()).isEqualTo("테마1");
		assertThat(themeResponse.description()).isEqualTo("첫번째테마");
		assertThat(themeResponse.thumbnail()).isEqualTo("테마이미지");

		// create reservation
		// given
		ReservationRequest reservationRequest = new ReservationRequest("tester", "2024-06-06", 1L, 1L);

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
		long reservationId = reservationResponse.id();

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
		long reservationTimeId = reservationTimeResponse.id();

		// when
		var deleteReservationTime = this.restTemplate.exchange(
				"http://localhost:" + this.port + "/times/" + reservationTimeId, HttpMethod.DELETE, null, Void.class);

		// then
		assertThat(deleteReservationTime.getStatusCode()).isEqualTo(HttpStatus.OK);

		// delete theme
		// given
		long themeId = themeResponse.id();

		// when
		var deleteTheme = this.restTemplate.exchange("http://localhost:" + this.port + "/themes/" + themeId,
				HttpMethod.DELETE, null, Void.class);

		// then
		assertThat(deleteTheme.getStatusCode()).isEqualTo(HttpStatus.OK);
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
		long reservationTimeId = reservationTimeResponse.id();

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

	@Test
	void themeControllerEndpoints() {
		// create theme
		// given
		ThemeRequest themeRequest = new ThemeRequest("테마1", "첫번째테마", "썸네일이미지");

		// when
		var createResponse = this.restTemplate.postForEntity("http://localhost:" + this.port + "/themes", themeRequest,
				ThemeResponse.class);

		// then
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		ThemeResponse themeResponse = createResponse.getBody();
		assertThat(themeResponse).isNotNull();
		assertThat(themeResponse.name()).isEqualTo("테마1");
		assertThat(themeResponse.description()).isEqualTo("첫번째테마");
		assertThat(themeResponse.thumbnail()).isEqualTo("썸네일이미지");

		// get themes
		// when
		var themesResponse = this.restTemplate.getForEntity("http://localhost:" + this.port + "/themes", List.class);

		// then
		assertThat(themesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		var themes = themesResponse.getBody();
		assertThat(themes).isNotNull();
		assertThat(themes.size()).isEqualTo(1);

		// delete theme
		// given
		long themeId = themeResponse.id();

		// when
		var deleteResponse = this.restTemplate.exchange("http://localhost:" + this.port + "/themes/" + themeId,
				HttpMethod.DELETE, null, Void.class);

		// then
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		// check theme delete
		// when
		themesResponse = this.restTemplate.getForEntity("http://localhost:" + this.port + "/themes", List.class);

		// then
		assertThat(themesResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		themes = themesResponse.getBody();
		assertThat(themes).isNotNull();
		assertThat(themes.size()).isEqualTo(0);
	}

}
