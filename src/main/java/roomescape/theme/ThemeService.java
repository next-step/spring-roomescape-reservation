package roomescape.theme;

import org.springframework.stereotype.Service;
import roomescape.exception.ReferentialIntegrityException;
import roomescape.reservation.ReservationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {
	private final ThemeRepository themeRepository;
	private final ReservationRepository reservationRepository;

	public ThemeService(ThemeRepository themeRepository, ReservationRepository reservationRepository) {
		this.themeRepository = themeRepository;
		this.reservationRepository = reservationRepository;
	}

	public List<ThemeResponse> findThemes() {
		return themeRepository.find().stream()
				.map(ThemeResponse::new)
				.collect(Collectors.toList());
	}

	public Long saveThemes(ThemeRequest request) {
		return themeRepository.save(request.getName(), request.getDescription(), request.getThumbnail());
	}

	public ThemeResponse findTheme(Long id) {
		return new ThemeResponse(themeRepository.findById(id));
	}

	public void deleteTheme(long id) {
		if(reservationRepository.countByTheme(id) > 0) {
			throw new ReferentialIntegrityException("해당 테마에 대한 예약");
		}

		themeRepository.delete(id);
	}
}
