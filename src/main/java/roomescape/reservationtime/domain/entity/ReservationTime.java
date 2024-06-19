package roomescape.reservationtime.domain.entity;

public class ReservationTime {
    private Long id;
    private String startAt;

    private ReservationTime() {
    }

    private ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime of(Long id, String startAt) {
        return new ReservationTime(id, startAt);
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
