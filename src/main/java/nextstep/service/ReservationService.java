package nextstep.service;

import nextstep.domain.Reservation;
import nextstep.dto.ReservationCreateRequest;
import nextstep.dto.ReservationFindAllResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private Long tmpId;
    private final List<Reservation> reservations;

    public ReservationService() {
        this.tmpId = 1L;
        this.reservations = new ArrayList<>();
    }

    public Long createReservation(ReservationCreateRequest reservationCreateRequest) {
        LocalDate date = parseDate(reservationCreateRequest.getDate());
        LocalTime time = parseTime(reservationCreateRequest.getTime());
        String name = reservationCreateRequest.getName();

        checkReservationAvailable(date, time);

        Reservation reservation = new Reservation(tmpId++, date, time, name);
        reservations.add(reservation);
        return reservation.getId();
    }

    private void checkReservationAvailable(LocalDate date, LocalTime time) {
        if (reservations.stream()
                .anyMatch(it -> it.equalsDateAndTime(date, time))) {
            throw new IllegalArgumentException("동시간대에 이미 예약이 존재합니다.");
        }
    }

    public ReservationFindAllResponse findAllReservations(String inputDate) {
        LocalDate date = parseDate(inputDate);

        List<Reservation> findReservations = reservations.stream()
                .filter(it -> it.equalsDate(date))
                .collect(Collectors.toList());
        return ReservationFindAllResponse.from(findReservations);
    }

    public void deleteReservation(String inputDate, String inputTime) {
        LocalDate date = parseDate(inputDate);
        LocalTime time = parseTime(inputTime);

        reservations.removeIf(it -> it.equalsDateAndTime(date, time));
    }
    private LocalDate parseDate(String date) {
        return LocalDate.parse(date);
    }

    private LocalTime parseTime(String time) {
        return LocalTime.parse(time + ":00");
    }
}
