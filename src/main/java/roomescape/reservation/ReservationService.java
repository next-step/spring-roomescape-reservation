package roomescape.reservation;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;

	public ReservationService(ReservationRepository reservationRepository) {
		this.reservationRepository = reservationRepository;
	}

	public List<ReservationResponse> findReservations() {
		return reservationRepository.find().stream()
				.map(ReservationResponse::new)
				.collect(Collectors.toList());
	}

	public ReservationResponse findReservation(Long id) {
		return new ReservationResponse(reservationRepository.findByKey(id));
	}

	public Long saveReservation(ReservationRequest request) {
		return reservationRepository.save(request.getName(), request.getDate(), request.getTimeId());
	}

	public void deleteReservation(long id) {
		reservationRepository.delete(id);
	}
}
