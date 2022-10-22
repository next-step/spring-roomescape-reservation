package nextstep.api.reservation.dto;

import nextstep.domain.reservation.model.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationCreateRequest {
    private String date;
    private String time;
    private String name;

    public ReservationCreateRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Reservation toEntity() {
        return new Reservation(null, LocalDate.parse(date), LocalTime.parse(time + ":00"), name);
    }
}
