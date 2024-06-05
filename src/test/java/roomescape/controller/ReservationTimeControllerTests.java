package roomescape.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.controller.dto.ReservationTimeRequest;
import roomescape.controller.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class ReservationTimeControllerTests {

	@InjectMocks
	private ReservationTimeController reservationTimeController;

	@Mock
	private ReservationTimeService reservationTimeService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void create() {
		// given
		var reservationTimeRequest = new ReservationTimeRequest("10:10");
		var reservationTimeResponse = new ReservationTimeResponse(1L, "10:10");

		given(this.reservationTimeService.create(reservationTimeRequest)).willReturn(reservationTimeResponse);

		// when
		ResponseEntity<ReservationTimeResponse> responseEntity = this.reservationTimeController
			.create(reservationTimeRequest);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(reservationTimeResponse);
	}

	@Test
	void getReservationTimes() {
		// given
		List<ReservationTimeResponse> reservationTimes = new ArrayList<>();

		given(this.reservationTimeService.getReservationTimes()).willReturn(reservationTimes);

		// when
		ResponseEntity<List<ReservationTimeResponse>> responseEntity = this.reservationTimeController
			.getReservationTimes();

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isEqualTo(reservationTimes);
	}

	@Test
	void delete() {
		// given
		long id = 1L;

		// when
		ResponseEntity<Void> responseEntity = this.reservationTimeController.delete(id);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		verify(this.reservationTimeService).delete(id);
	}

}
