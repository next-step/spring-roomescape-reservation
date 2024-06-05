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

	public Long saveReservationTime(ReservationTimeRequest request) {
		return reservationTimeRepository.save(request.getStartAt());
	}

	public ReservationTimeResponse findReservationTime(Long id) {
		return reservationTimeRepository.findById(id).convert();
	}

	public List<ReservationTimeResponse> findReservationTimes() {
		return reservationTimeRepository.find().stream()
				.map(ReservationTime::convert)
				.collect(Collectors.toList());
	}

	public void deleteReservation(Long id) {
		reservationTimeRepository.delete(id);
	}
}
