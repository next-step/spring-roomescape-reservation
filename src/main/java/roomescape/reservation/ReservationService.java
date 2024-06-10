package roomescape.reservation;

import org.springframework.stereotype.Service;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.ReservationTimeRepository;
import roomescape.reservationTime.ReservationTimeService;
import roomescape.theme.Theme;
import roomescape.theme.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
	private final ReservationRepository reservationRepository;
	private final ReservationTimeRepository reservationTimeRepository;
	private final ThemeRepository themeRepository;

	public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository, ThemeRepository themeRepository) {
		this.reservationRepository = reservationRepository;
		this.reservationTimeRepository = reservationTimeRepository;
		this.themeRepository = themeRepository;
	}

	public List<ReservationResponse> findReservations() {
		return reservationRepository.find().stream()
				.map(ReservationResponse::new)
				.collect(Collectors.toList());
	}

	public ReservationResponse findReservation(Long id) {
		return new ReservationResponse(reservationRepository.findByKey(id));
	}

	public ReservationResponse saveReservation(ReservationRequest request) {
		ReservationTime reservationTime = reservationTimeRepository.findById(request.getTimeId());
		Theme theme = themeRepository.findById(request.getThemeId());

		Reservation reservation = new Reservation(request.getName(), request.getDate(), reservationTime, theme);

		return new ReservationResponse(reservationRepository.save(reservation));
	}

	public void deleteReservation(long id) {
		reservationRepository.delete(id);
	}
}
