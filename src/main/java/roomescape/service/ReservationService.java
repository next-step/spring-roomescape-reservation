package roomescape.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTheme;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.exception.BusinessException;
import roomescape.repository.JdbcReservationDao;
import roomescape.repository.JdbcReservationThemeDao;
import roomescape.repository.JdbcReservationTimeDao;

@Service
public class ReservationService {

    private final JdbcReservationDao reservationDao;
    private final JdbcReservationTimeDao reservationTimeDao;
    private final JdbcReservationThemeDao reservationThemeDao;

    public ReservationService(JdbcReservationDao reservationDao, JdbcReservationTimeDao reservationTimeDao,
                              JdbcReservationThemeDao reservationThemeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.reservationThemeDao = reservationThemeDao;
    }

    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        validateReservationCreation(reservationRequest);

        Reservation reservation = reservationDao.save(this.convertToEntity(reservationRequest));
        return this.convertToResponse(reservation);
    }

    public List<ReservationResponse> findAllReservations() {
        return reservationDao.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

    public void deleteReservation(Long id) {
        reservationDao.delete(id);
    }

    private void validateReservationCreation(ReservationRequest reservationRequest) {
        findReservationThemeById(reservationRequest.getThemeId());
        ReservationTime reservationTime = findReservationTimeById(reservationRequest.getTimeId());

        long count = reservationDao.countByDateAndTimeId(reservationRequest.getDate()
                , reservationRequest.getTimeId());
        if (count > 0) {
            throw new BusinessException("해당 시간에 이미 예약이 존재합니다.", HttpStatus.CONFLICT);
        }

        if (isDateExpired(reservationRequest.getDate(), reservationTime.getStartAt())) {
            throw new BusinessException("이미 지나간 날짜는 예약할 수 없습니다.", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private ReservationResponse convertToResponse(Reservation reservation) {
        return new ReservationResponse(reservation.getId(), reservation.getName(), reservation.getDate(),
                reservation.getTime().getStartAt(), reservation.getTheme().getName());
    }

    private Reservation convertToEntity(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = findReservationTimeById(reservationRequest.getTimeId());
        ReservationTheme reservationTheme = findReservationThemeById(reservationRequest.getThemeId());

        return new Reservation(reservationRequest.getName(), reservationRequest.getDate(), reservationTime,
                reservationTheme);
    }

    private ReservationTime findReservationTimeById(String timeId) {
        return reservationTimeDao
                .findById(Long.parseLong(timeId))
                .orElseThrow(() -> new BusinessException("존재하지 않는 예약시간입니다.", HttpStatus.BAD_REQUEST));
    }

    private ReservationTheme findReservationThemeById(String themeId) {
        return reservationThemeDao
                .findById(Long.parseLong(themeId))
                .orElseThrow(() -> new BusinessException("존재하지 않는 예약테마입니다.", HttpStatus.BAD_REQUEST));
    }

    private boolean isDateExpired(String date, String startAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reservationDateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(startAt));

        return reservationDateTime.isBefore(now);
    }
}
