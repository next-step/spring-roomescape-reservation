package nextstep.roomescape.web.dto;

import nextstep.roomescape.core.domain.Reservation;

public class ReservationDto {

    private final Integer id;
    private final Integer scheduleId;
    private final String name;

    public ReservationDto(Integer id, Integer scheduleId, String name) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.name = name;
    }

    public Reservation toEntity() {
        return new Reservation(id, scheduleId, name);
    }

    public Integer getId() {
        return id;
    }

    public Integer getScheduleId() {
        return scheduleId;
    }

    public String getName() {
        return name;
    }
}
