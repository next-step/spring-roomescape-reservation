package roomescape.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.repository.ReservationTimeDao;
import roomescape.service.ReservationTimeService;

@Service
public class ReservationTimeServiceImpl implements ReservationTimeService {

    private final ReservationTimeDao reservationTimeDao;

    public ReservationTimeServiceImpl(ReservationTimeDao reservationTimeDao) {
        this.reservationTimeDao = reservationTimeDao;
    }

    @Override
    public ReservationTimeResponse createReservationTime(ReservationTimeRequest reservationTimeRequest) {
        ReservationTime reservationTime = reservationTimeDao.save(convertToEntity(reservationTimeRequest));
        return this.convertToResponse(reservationTime);
    }

    @Override
    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeDao.findAll()
                .stream()
                .map(reservationTime -> this.convertToResponse(reservationTime))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteReservationTime(Long id) {
        reservationTimeDao.delete(id);
    }

    private ReservationTimeResponse convertToResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    private ReservationTime convertToEntity(ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(reservationTimeRequest.getStartAt());
    }
}
