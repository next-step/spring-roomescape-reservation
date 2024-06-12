package roomescape.reservation.service;

import com.sun.jdi.request.DuplicateRequestException;
import org.springframework.stereotype.Service;
import roomescape.error.exception.PastDateTimeException;
import roomescape.error.exception.ReservationTimeNotExistsException;
import roomescape.error.exception.ThemeNotExistsException;
import roomescape.reservation.Reservation;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservationTime.ReservationTime;
import roomescape.reservationTime.service.ReservationTimeRepository;
import roomescape.theme.Theme;
import roomescape.theme.service.ThemeRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository,
        ReservationTimeRepository reservationTimeRepository, ThemeRepository themeRepository) {
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
        ReservationTime reservationTime = Optional.ofNullable(
                reservationTimeRepository.findById(request.getTimeId()))
            .orElseThrow(ReservationTimeNotExistsException::new);
        Theme theme = Optional.ofNullable(themeRepository.findById(request.getThemeId()))
            .orElseThrow(ThemeNotExistsException::new);

        Reservation reservation = new Reservation(request.getName(), request.getDate(),
            reservationTime, theme);

        if (reservation.isBeforeThenNow()) {
            throw new PastDateTimeException();
        }

        if (reservationRepository.countByDateAndTimeAndTheme(reservation.getDate(),
            reservationTime.getId(), theme.getId()) > 0) {
            throw new DuplicateRequestException("해당 시간 예약이");
        }

        return new ReservationResponse(reservationRepository.save(reservation));
    }

    public void deleteReservation(long id) {
        reservationRepository.delete(id);
    }
}
