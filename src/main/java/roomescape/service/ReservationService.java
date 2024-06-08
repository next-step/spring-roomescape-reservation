package roomescape.service;

import java.util.List;

import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.repository.ReservationRepository;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;

	private final ReservationTimeService reservationTimeService;

	ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService) {
		this.reservationRepository = reservationRepository;
		this.reservationTimeService = reservationTimeService;
	}

	public List<ReservationResponse> getReservations() {
		return this.reservationRepository.findAll()
			.stream()
			.map((reservation) -> ReservationResponse.from(reservation, reservation.getTime()))
			.toList();
	}

	public ReservationResponse create(ReservationRequest request) {
		var reservationTime = this.reservationTimeService.getReservationTimeById(request.timeId());
		var reservation = Reservation.builder().name(request.name()).date(request.date()).time(reservationTime).build();
		var savedReservation = this.reservationRepository.save(reservation);
		return ReservationResponse.from(savedReservation, reservationTime);
	}

	public void cancel(long id) {
		this.reservationRepository.delete(id);
	}

}
