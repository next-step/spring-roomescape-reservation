package roomescape.reservation.application;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import roomescape.reservation.dto.ReservationResponse;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.domain.repository.ReservationRepository;
import roomescape.reservation.exception.ReservationAlreadyExistsException;
import roomescape.reservation.dto.ReservationCreateRequest;
import roomescape.theme.domain.Theme;
import roomescape.theme.infrastructure.JdbcThemeRepository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.infrastructure.JdbcReservationTimeRepository;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final JdbcReservationTimeRepository reservationTimeRepository;
    private final JdbcThemeRepository themeRepository;

    public ReservationService(ReservationRepository reservationRepository,
            JdbcReservationTimeRepository reservationTimeRepository,
            JdbcThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
    }

    public ReservationResponse save(ReservationCreateRequest request) {
        ReservationTime findReservationTime = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 예약 시간입니다."));
        Theme findTheme = themeRepository.findById(request.themeId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 테마입니다."));

        if (reservationRepository.existsByDateAndTimeId(request.date(), findReservationTime.getId())) {
            throw new ReservationAlreadyExistsException("이미 예약된 시간입니다.");
        }

        Reservation reservation = reservationRepository.save(request.toReservation(findReservationTime, findTheme));
        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void cancelReservation(Long reservationId) {
        if (!reservationRepository.existsById(reservationId)) {
            throw new IllegalArgumentException("존재하지 않는 예약입니다.");
        }
        reservationRepository.deleteById(reservationId);
    }
}
