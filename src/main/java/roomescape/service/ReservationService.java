package roomescape.service;

import java.util.List;

import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationRepository;

import org.springframework.stereotype.Service;

import static roomescape.controller.dto.ReservationRequest.validateReservation;

@Service
public class ReservationService {

	private final ReservationRepository reservationRepository;

	private final ReservationTimeService reservationTimeService;

	private final ThemeService themeService;

	ReservationService(ReservationRepository reservationRepository, ReservationTimeService reservationTimeService,
			ThemeService themeService) {
		this.reservationRepository = reservationRepository;
		this.reservationTimeService = reservationTimeService;
		this.themeService = themeService;
	}

	public List<ReservationResponse> getReservations() {
		return this.reservationRepository.findAll()
			.stream()
			.map((reservation) -> ReservationResponse.from(reservation, reservation.getTime(), reservation.getTheme()))
			.toList();
	}

	public ReservationResponse create(ReservationRequest request) {

		var reservationTime = this.reservationTimeService.getReservationTimeById(request.timeId());

		validateReservation(request, reservationTime.getStartAt());

		if (this.reservationRepository.isDuplicateReservation(request)) {
			throw new RoomEscapeException(ErrorCode.DUPLICATE_RESERVATION);
		}

		var theme = this.themeService.getThemeById(request.themeId());
		var reservation = Reservation.builder()
			.name(request.name())
			.date(request.date())
			.time(reservationTime)
			.theme(theme)
			.build();
		var savedReservation = this.reservationRepository.save(reservation);
		return ReservationResponse.from(savedReservation, reservationTime, theme);
	}

	public void cancel(long id) {
		var isExist = this.reservationRepository.isExistId(id);
		if (isExist) {
			this.reservationRepository.delete(id);
		}
		else {
			throw new RoomEscapeException(ErrorCode.NOT_FOUND_RESERVATION);
		}
	}

}
