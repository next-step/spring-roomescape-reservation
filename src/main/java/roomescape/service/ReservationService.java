package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.dto.reservation.ReservationsResponse;
import roomescape.dto.reservation.create.ReservationCreateRequest;
import roomescape.dto.reservation.create.ReservationCreateResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.ThemeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationTimeRepository reservationTimeRepository;

    private final ThemeRepository themeRepository;


    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository,
                              ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
        this.themeRepository = themeRepository;
    }

    public List<ReservationsResponse> findReservations() {
        return reservationRepository.findReservations()
                .stream()
                .map(ReservationsResponse::new)
                .collect(Collectors.toList());
    }

    public ReservationCreateResponse createReservation(ReservationCreateRequest request) {
        ReservationTime time = reservationTimeRepository.findReservationTimeById(request.getTimeId());
        Theme theme = themeRepository.findThemeById(request.getThemeId());
        Reservation reservation = reservationRepository.createReservation(request, time, theme);
        return ReservationCreateResponse.fromEntity(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteReservation(id);
    }
}
