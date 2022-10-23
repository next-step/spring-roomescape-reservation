package nextstep.domain.reservation.model;

import nextstep.domain.Identity;

public class Reservation {
    private Long id;
    private String name;
    private Long scheduleId;

    public Reservation(Long id, String name, Long scheduleId) {
        this.id = id;
        this.name = name;
        this.scheduleId = scheduleId;
    }

    public Reservation withId() {
        this.id = Identity.getId(Reservation.class);
        return this;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getScheduleId() {
        return scheduleId;
    }
}
