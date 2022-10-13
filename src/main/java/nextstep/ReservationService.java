package nextstep;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationDatabase reservationDatabase;

    public ReservationService(ReservationDatabase reservationDatabase) {
        this.reservationDatabase = reservationDatabase;
    }

    public ReservationResponse createReservation(ReservationCreateRequest request) {
        if (reservationDatabase.existsReservation(request.getDate(), request.getTime())) {
            throw new IllegalStateException("해당 일시에 이미 예약이 존재하여 예약이 불가능합니다.");
        }
        Reservation reservation = reservationDatabase.save(request.toObject());
        return ReservationResponse.from(reservation);
    }

    public List<ReservationResponse> findReservationByDate(String date) {
        return reservationDatabase.findByDate(LocalDate.parse(date)).stream()
                .map(ReservationResponse::from)
                .toList();
    }

    public void cancelReservation(ReservationDeleteRequest request) {
        reservationDatabase.deleteByDateAndTime(request.getDate(), request.getTime());
    }
}
