package roomescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation addReservation(ReservationRequest reservationRequest) {
        // timeId를 Long으로 변환하고, 예약 시간을 조회
        Long timeId = reservationRequest.getTimeIdAsLong();
        if (timeId == null) {
            throw new IllegalArgumentException("Invalid time ID");
        }

        ReservationTime reservationTime = reservationTimeRepository.findById(timeId);
        if (reservationTime == null) {
            throw new IllegalArgumentException("Reservation time not found");
        }

        // Reservation 객체 생성
        Reservation reservation = new Reservation();
        reservation.setName(reservationRequest.getName());
        reservation.setDate(reservationRequest.getDate());
        reservation.setTime(reservationTime);

        // 예약 저장
        reservationRepository.save(reservation);
        return reservation;
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
