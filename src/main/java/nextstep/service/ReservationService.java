package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.domain.Reservations;
import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ReservationFindAllResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {
    public static final String DUPLICATE_RESERVATION_MESSAGE = "동시간대에 이미 예약이 존재합니다.";

    private final Reservations reservations;

    public ReservationService(Reservations reservations) {
        this.reservations = reservations;
    }

    public Long createReservation(ReservationCreateRequest reservationCreateRequest) {
        LocalDate date = parseDate(reservationCreateRequest.getDate());
        LocalTime time = parseTime(reservationCreateRequest.getTime());
        String name = reservationCreateRequest.getName();

        checkReservationAvailable(date, time);

        Reservation reservation = reservations.save(date, time, name);
        return reservation.getId();
    }

    private void checkReservationAvailable(LocalDate date, LocalTime time) {
        if (reservations.existsByDateAndTime(date, time)) {
            throw new IllegalArgumentException(DUPLICATE_RESERVATION_MESSAGE);
        }
    }

    public ReservationFindAllResponse findAllReservations(String inputDate) {
        LocalDate date = parseDate(inputDate);

        List<Reservation> findReservations = reservations.findAllByDate(date);
        return ReservationFindAllResponse.from(findReservations);
    }

    public void deleteReservation(String inputDate, String inputTime) {
        LocalDate date = parseDate(inputDate);
        LocalTime time = parseTime(inputTime);

        reservations.deleteByDateAndTime(date, time);
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    private LocalTime parseTime(String time) {
        return LocalTime.parse(time + ":00");
    }
}
