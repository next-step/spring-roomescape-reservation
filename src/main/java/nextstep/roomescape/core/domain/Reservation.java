package nextstep.roomescape.core.domain;

public class Reservation {

    private final Integer id;
    private final Integer scheduleId;
    private final String name;

    public Reservation(Integer id, Integer scheduleId, String name) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.name = name;
    }

    public Integer id() {
        return id;
    }

    public Integer scheduleId() {
        return scheduleId;
    }

    public String name() {
        return name;
    }
}
