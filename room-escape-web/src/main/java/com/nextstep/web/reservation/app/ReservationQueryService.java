package com.nextstep.web.reservation.app;

import com.nextstep.web.reservation.dto.ReservationResponse;
import com.nextstep.web.reservation.repository.ReservationDao;
import com.nextstep.web.reservation.repository.entity.ReservationEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ReservationQueryService {
    private final ReservationDao reservationDao;

    public ReservationQueryService(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }

    public List<ReservationResponse> findAllBy(String date) {
        final List<ReservationEntity> reservations = reservationDao.findAllBy(date);
        return ReservationResponse.toListFromEntity(reservations);
    }
}
