package roomescape.service;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import roomescape.domain.ReservationTime;
import roomescape.dto.request.ReservationTimeRequest;
import roomescape.dto.response.ReservationTimeResponse;
import roomescape.exception.BusinessException;
import roomescape.repository.JdbcReservationDao;
import roomescape.repository.JdbcReservationTimeDao;

@Service
public class ReservationTimeService {

    private final JdbcReservationTimeDao reservationTimeDao;
    private final JdbcReservationDao reservationDao;

    public ReservationTimeService(JdbcReservationTimeDao reservationTimeDao, JdbcReservationDao reservationDao) {
        this.reservationTimeDao = reservationTimeDao;
        this.reservationDao = reservationDao;
    }

    public ReservationTimeResponse createReservationTime(ReservationTimeRequest reservationTimeRequest) {
        Long count = reservationTimeDao.findByStartAt(reservationTimeRequest.getStartAt());
        if (count > 0) {
            throw new BusinessException("동일한 테마시간이 존재합니다.", HttpStatus.CONFLICT);
        }

        ReservationTime reservationTime = reservationTimeDao.save(convertToEntity(reservationTimeRequest));
        return this.convertToResponse(reservationTime);
    }

    public List<ReservationTimeResponse> findAllReservationTimes() {
        return reservationTimeDao.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public void deleteReservationTime(Long id) {
        long count = reservationDao.countByTimeId(id);
        if (count > 0) {
            throw new BusinessException("예약 시간을 사용중인 예약이 존재합니다. 예약 삭제 후 다시 시도해주세요."
                    , HttpStatus.CONFLICT);
        }
        reservationTimeDao.delete(id);
    }

    private ReservationTimeResponse convertToResponse(ReservationTime reservationTime) {
        return new ReservationTimeResponse(reservationTime.getId(), reservationTime.getStartAt());
    }

    private ReservationTime convertToEntity(ReservationTimeRequest reservationTimeRequest) {
        return new ReservationTime(reservationTimeRequest.getStartAt());
    }
}
