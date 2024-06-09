package roomescape.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTheme;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationRequest;
import roomescape.dto.response.ReservationResponse;
import roomescape.repository.ReservationDao;
import roomescape.service.ReservationService;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationDao reservationDao;

    public ReservationServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    @Override
    public ReservationResponse createReservation(ReservationRequest request) {
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
        ReservationTime reservationTime = new ReservationTime(Long.parseLong(reservationRequest.getTimeId()));
        ReservationTheme reservationTheme = new ReservationTheme(Long.parseLong(reservationRequest.getThemeId()));

        return new Reservation(
                reservationRequest.getName()
                , reservationRequest.getDate()
                , reservationTime
                , reservationTheme);
    }
}
