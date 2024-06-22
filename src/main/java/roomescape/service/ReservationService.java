package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.domain.Theme;
import roomescape.dto.reservation.ReservationsResponse;
import roomescape.dto.reservation.create.ReservationCreateRequest;
import roomescape.dto.reservation.create.ReservationCreateResponse;
import roomescape.dto.theme.ThemeResponse;
import roomescape.dto.time.ReservationTimeResponse;
import roomescape.exception.ErrorCode;
import roomescape.exception.ErrorCodeResponse;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;
import roomescape.repository.ThemeRepository;

import java.time.LocalDate;
import java.time.LocalTime;
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
        checkReservationTime(request);
        ReservationTime time = reservationTimeRepository.findReservationTimeById(request.getTimeId());
        Theme theme = themeRepository.findThemeById(request.getThemeId());
        Reservation reservation = reservationRepository.createReservation(request, time, theme);
        return ReservationCreateResponse.fromEntity(reservation);
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteReservation(id);
    }

    public void checkReservationTime(ReservationCreateRequest request){
        //예약 시간
        ReservationTime reservationTime = reservationTimeRepository.findReservationTimeById(request.getTimeId());
        ReservationTimeResponse reservatedTime = ReservationTimeResponse.toDto(reservationTime);
        LocalTime currentTime = LocalTime.now();

        //예약 날짜
        LocalDate reservatedDate = LocalDate.parse(request.getDate());
        LocalDate currentDate = LocalDate.now();

        if (reservatedDate.isBefore(currentDate) ||
                reservatedDate.equals(currentDate) && reservatedTime.getStartAt().isBefore(currentTime)) {
            throw new ErrorCodeResponse(ErrorCode.INVALID_TIME_ZONE, "예약 시간을 다시 확인해주세요.");
        }
    }
}
