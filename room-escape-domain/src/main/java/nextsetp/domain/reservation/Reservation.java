package nextsetp.domain.reservation;

import java.time.LocalDateTime;

public class Reservation {
    private Long scheduleId;
    private LocalDateTime reservationTime;
    private String name;

    public Reservation(Long scheduleId, LocalDateTime reservationTime, String name) {
        this.scheduleId = scheduleId;
        this.reservationTime = reservationTime;
        this.name = name;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public String getName() {
        return name;
    }
}
