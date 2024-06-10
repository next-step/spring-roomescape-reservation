package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.domain.Reservation;
import roomescape.exception.ErrorCode;
import roomescape.exception.RoomEscapeException;
import roomescape.repository.ReservationRepository;

import org.springframework.stereotype.Service;

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

		checkReservationAvailability(request, reservationTime.getStartAt());

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

	private void checkReservationAvailability(ReservationRequest request, String time) {
		LocalDate reservationDate = LocalDate.parse(request.date());
		LocalTime reservationTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
		if (reservationDate.isBefore(LocalDate.now())
				|| (reservationDate.isEqual(LocalDate.now()) && reservationTime.isBefore(LocalTime.now()))) {
			throw new RoomEscapeException(ErrorCode.PAST_RESERVATION);
		}

		if (this.reservationRepository.isDuplicateReservation(request.date(), request.themeId())) {
			throw new RoomEscapeException(ErrorCode.DUPLICATE_RESERVATION);
		}
	}

}
