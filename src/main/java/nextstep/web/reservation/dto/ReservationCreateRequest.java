package nextstep.web.reservation.dto;

import nextstep.domain.reservation.model.Reservation;

public class ReservationCreateRequest {
    private String name;
    private Long scheduleId;

    public ReservationCreateRequest(String name, Long scheduleId) {
        this.name = name;
        this.scheduleId = scheduleId;
    }

    public Reservation toEntity() {
        return new Reservation(null, name, scheduleId);
    }
}
