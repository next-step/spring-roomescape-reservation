package roomescape.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReservationTimeTests {

	@InjectMocks
	private ReservationTimeService reservationTimeService;

	@Mock
	private ReservationTimeRepository reservationTimeRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getReservationTimes() {
		// given
		List<ReservationTime> reservationTimes = new ArrayList<>();

		ReservationTime reservationTime = ReservationTime.builder().id(1L).startAt("10:00").build();

		reservationTimes.add(reservationTime);

		given(this.reservationTimeRepository.findAll()).willReturn(reservationTimes);

		// when
		var resultReservationTimes = this.reservationTimeService.getReservationTimes();

		// then
		assertThat(resultReservationTimes).isNotEmpty();
		assertThat(resultReservationTimes).hasSize(1);
		assertThat(resultReservationTimes).allSatisfy((reservationResponse) -> {
			assertThat(reservationResponse.id()).isEqualTo(1L);
			assertThat(reservationResponse.startAt()).isEqualTo("10:00");
		});
	}

	@Test
	void delete() {
		// given
		long id = 1L;

		// when
		this.reservationTimeService.delete(id);

		// then
		verify(this.reservationTimeRepository, times(1)).delete(id);
	}

	@Test
	void getReservationTimeById() {
		// given
		ReservationTime reservationTime = ReservationTime.builder().id(1L).startAt("10:00").build();

		given(this.reservationTimeRepository.findById(anyLong())).willReturn(reservationTime);

		// when
		var resultReservationTimeById = this.reservationTimeService.getReservationTimeById(1L);

		// then
		assertThat(resultReservationTimeById).isNotNull();
		assertThat(resultReservationTimeById.getId()).isEqualTo(1L);
		assertThat(resultReservationTimeById.getStartAt()).isEqualTo("10:00");
	}

}
