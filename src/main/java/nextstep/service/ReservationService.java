package nextstep.service;

import nextstep.Reservation;
import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 예약 생성
    public Long create(Reservation reservation) {
        Reservation res = reservationRepository.save(reservation);
        return res.getId();
    }
    // 예약 조회

    // 예약 취소

}
// 형님 카카오톡 장애나서 대응하러 갑니다.
