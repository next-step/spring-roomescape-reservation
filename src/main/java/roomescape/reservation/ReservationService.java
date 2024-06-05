package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
	private ReservationRepository reservationRepository;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public List<ReservationResponse> getReservations() {
		return reservationRepository.findReservation().stream()
				.map(Reservation::convert)
				.collect(Collectors.toList());
	}

	public ReservationResponse saveReservation(ReservationRequest request) {
		Long id = reservationRepository.saveReservation(request.getName(), request.getDate(), request.getTime());

		return reservationRepository.findReservationByKey(id).convert();
	}

	public void deleteReservation(long id) {
		reservationRepository.deleteReservation(id);
	}
}
