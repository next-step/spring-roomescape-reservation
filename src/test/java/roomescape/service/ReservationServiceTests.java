package roomescape.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import roomescape.controller.dto.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ReservationServiceTests {

	@InjectMocks
	private ReservationService reservationService;

	@Mock
	private ReservationTimeService reservationTimeService;

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private ReservationTimeRepository reservationTimeRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getReservations() {
		// given
		List<Reservation> reservations = new ArrayList<>();

		ReservationTime reservationTime = ReservationTime.builder().id(1L).startAt("10:00").build();

		Reservation reservation = Reservation.builder()
			.id(1L)
			.name("tester")
			.date("2024-06-06")
			.time(reservationTime)
			.build();
		reservations.add(reservation);

		given(this.reservationRepository.findAll()).willReturn(reservations);

		// when
		var resultReservations = this.reservationService.getReservations();

		// then
		assertThat(resultReservations).isNotEmpty();
		assertThat(resultReservations).hasSize(1);
		assertThat(resultReservations).allSatisfy((reservationResponse) -> {
			assertThat(reservationResponse.id()).isEqualTo(1L);
			assertThat(reservationResponse.name()).isEqualTo("tester");
			assertThat(reservationResponse.date()).isEqualTo("2024-06-06");
			assertThat(reservationResponse.time().startAt()).isEqualTo("10:00");
		});
	}

	@Test
	void create() {
		// given
		ReservationRequest request = new ReservationRequest("tester", "2024-06-06", 1L);

		ReservationTime reservationTime = ReservationTime.builder().id(1L).startAt("10:00").build();

		Reservation reservation = Reservation.builder()
			.id(1L)
			.name("tester")
			.date("2024-06-06")
			.time(reservationTime)
			.build();

		given(this.reservationTimeService.getReservationTimeById(1L)).willReturn(reservationTime);
		given(this.reservationTimeRepository.findById(1L)).willReturn(reservationTime);
		given(this.reservationRepository.save(any(Reservation.class))).willReturn(reservation);

		// when
		var createdReservation = this.reservationService.create(request);

		// then
		assertThat(createdReservation).isNotNull();
		assertThat(createdReservation.id()).isEqualTo(1L);
		assertThat(createdReservation.name()).isEqualTo("tester");
		assertThat(createdReservation.date()).isEqualTo("2024-06-06");
		assertThat(createdReservation.time()).isNotNull();
		assertThat(createdReservation.time().id()).isEqualTo(1L);
		assertThat(createdReservation.time().startAt()).isEqualTo("10:00");
	}

	@Test
	void cancel() {
		// given
		long id = 1L;

		// when
		this.reservationService.cancel(id);

		// then
		verify(this.reservationRepository, times(1)).delete(id);
	}

}
