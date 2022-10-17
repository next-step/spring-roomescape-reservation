package nextstep.reservation;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse createReservation(ReservationCreateRequest request) {
        if (reservationRepository.existsReservation(request.getScheduleId(), request.getDate(), request.getTime())) {
            throw new IllegalStateException("해당 일시에 이미 예약이 존재하여 예약이 불가능합니다.");
        }
        Reservation reservation = reservationRepository.save(request.toObject());
        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> findReservationByDate(String date) {
        return reservationRepository.findByDate(LocalDate.parse(date)).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void cancelReservation(ReservationDeleteRequest request) {
        if (reservationRepository.deleteByScheduleIdAndDateAndTime(request.getScheduleId(), request.getDate(), request.getTime())) {
            return;
        }
        throw new IllegalStateException("예약을 취소하는데 실패했습니다.");
    }
}
