package roomescape.reservationTime;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationTimeService {
	private final ReservationTimeRepository reservationTimeRepository;

	public ReservationTimeService(ReservationTimeRepository reservationTimeRepository) {
		this.reservationTimeRepository = reservationTimeRepository;
	}

	public ReservationTimeResponse saveReservationTime(ReservationTimeRequest request) {
		ReservationTime reservationTime = new ReservationTime(request.getStartAt());
		return new ReservationTimeResponse(reservationTimeRepository.save(reservationTime));
	}

	public ReservationTimeResponse findReservationTime(Long id) {
		return new ReservationTimeResponse(reservationTimeRepository.findById(id));
	}

	public List<ReservationTimeResponse> findReservationTimes() {
		return reservationTimeRepository.find().stream()
				.map(ReservationTimeResponse::new)
				.collect(Collectors.toList());
	}

	public void deleteReservation(Long id) {
		reservationTimeRepository.delete(id);
	}
}
