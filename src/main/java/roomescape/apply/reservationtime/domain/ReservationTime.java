package roomescape.apply.reservationtime.domain;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTime {

    private Long id;
    private String startAt;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    protected ReservationTime() {

    }

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(String startAt) {
        ReservationTime reservation = new ReservationTime();
        reservation.startAt = startAt;
        return reservation;
    }

    public int compareByStartTime(ReservationTime target) {
        LocalTime time1 = LocalTime.parse(this.startAt, formatter);
        LocalTime time2 = LocalTime.parse(target.getStartAt(), formatter);
        return time1.compareTo(time2);
    }

    public void changeId(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
