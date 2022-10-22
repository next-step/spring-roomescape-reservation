package nextstep.service;

import nextstep.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    // 예약 생성
    public long create(LocalDate date, LocalTime time, String name) {
        return reservationRepository.save(date, time, name);
    }
    // 예약 조회

    // 예약 취소

}
