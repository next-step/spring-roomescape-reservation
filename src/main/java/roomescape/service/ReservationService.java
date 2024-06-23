package roomescape.service;

import org.springframework.stereotype.Service;
import roomescape.dto.ReservationRq;
import roomescape.dto.ReservationRs;
import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationTimeRepository reservationTimeRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public List<ReservationRs> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(reservation -> new ReservationRs(
                        reservation.getId(),
                        reservation.getName(),
                        reservation.getDate(),
                        reservation.getTime()
                ))
                .toList();
    }

    public ReservationRs addReservation(ReservationRq reservationRq) {
        // timeId를 Long으로 변환하고, 예약 시간을 조회
        Optional<Long> optionalTimeId = Optional.ofNullable(reservationRq.getTimeId());
        Long timeId = optionalTimeId.orElseThrow(
                () -> new IllegalArgumentException("Invalid time ID")
        );

        Optional<ReservationTime> optionalReservationTime = Optional.ofNullable(reservationTimeRepository.findById(timeId));
        ReservationTime reservationTime = optionalReservationTime.orElseThrow(
                () -> new IllegalArgumentException("Reservation time not found")
        );

        // Reservation 객체 생성
        Reservation reservation = new Reservation(
                null,
                reservationRq.getName(),
                reservationRq.getDate(),
                reservationTime
        );

        // 예약 저장
        Long id = reservationRepository.save(reservation);

        return new ReservationRs(
                id,
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public void deleteReservation(Long id) {
        reservationRepository.deleteById(id);
    }
}
