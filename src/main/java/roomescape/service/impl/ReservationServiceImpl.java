package roomescape.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTheme;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationDao;
import roomescape.repository.ReservationThemeDao;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;
    private final ReservationTimeDao reservationTimeDao;
    private final ReservationThemeDao reservationThemeDao;

    public ReservationServiceImpl(ReservationDao reservationDao, ReservationTimeDao reservationTimeDao,
                                  ReservationThemeDao reservationThemeDao) {
        this.reservationDao = reservationDao;
        this.reservationTimeDao = reservationTimeDao;
        this.reservationThemeDao = reservationThemeDao;
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
        Optional<ReservationTime> findReservationTime = reservationTimeDao
                .findById(Long.parseLong(request.getTimeId()));
        if (findReservationTime.isEmpty()) {
            throw new RuntimeException("존재하지 않는 예약시간입니다.");
        }

        Optional<ReservationTheme> findReservationTheme = reservationThemeDao
                .findById(Long.parseLong(request.getThemeId()));
        if (findReservationTheme.isEmpty()) {
            throw new RuntimeException("존재하지 않는 예약테마입니다.");
        }

        long count = reservationDao.countByDateAndTimeId(request.getDate(), request.getTimeId());
        if (count > 0) {
            throw new RuntimeException("해당 시간에 이미 예약이 존재합니다.");
        }

        Reservation reservation = reservationDao.save(this.convertToEntity(request));
        return this.convertToResponse(reservation);
    }

    @Override
    public List<ReservationResponse> findAllReservations() {
        return reservationDao.findAll()
                .stream()
                .map(reservation -> this.convertToResponse(reservation))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteReservation(Long id) {
        reservationDao.delete(id);
    }

    private ReservationResponse convertToResponse(Reservation reservation) {
        return new ReservationResponse(
                reservation.getId()
                , reservation.getName()
                , reservation.getDate()
                , reservation.getTime().getStartAt()
                , reservation.getTheme().getName());
    }


    private Reservation convertToEntity(ReservationRequest reservationRequest) {
        ReservationTime reservationTime = reservationTimeDao
                .findById(Long.parseLong(reservationRequest.getTimeId())).get();
        ReservationTheme reservationTheme = reservationThemeDao.findById(
                Long.parseLong(reservationRequest.getThemeId())).get();

        return new Reservation(
                reservationRequest.getName()
                , reservationRequest.getDate()
                , reservationTime
                , reservationTheme);
    }
}
