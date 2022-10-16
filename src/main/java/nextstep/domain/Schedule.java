package nextstep.domain;

public class Schedule {

    private Long id;
    private Long themeId;
    private Long reservationId;

    private Schedule() {
    }

    public Schedule(Long themeId, Long reservationId) {
        this(null, themeId, reservationId);
    }

    public Schedule(Long id, Long themeId, Long reservationId) {
        this.id = id;
        this.themeId = themeId;
        this.reservationId = reservationId;
    }

    public Long getId() {
        return id;
    }

    public Long getThemeId() {
        return themeId;
    }

    public Long getReservationId() {
        return reservationId;
    }
}
