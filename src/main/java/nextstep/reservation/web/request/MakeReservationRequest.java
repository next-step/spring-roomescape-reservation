package nextstep.reservation.web.request;

import java.time.LocalDate;
import java.time.LocalTime;
import nextstep.reservation.domain.Reservation;

public class MakeReservationRequest {

    private final Long scheduleId;
    private final String date;
    private final String time;
    private final String name;

    public MakeReservationRequest(Long scheduleId, String date, String time, String name) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.time = time;
        this.name = name;
    }

    public Reservation toReservation() {
        return new Reservation(scheduleId, LocalDate.parse(date), LocalTime.parse(time), name);
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
