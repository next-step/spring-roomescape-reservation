package roomescape.dto;

public class ReservationTimeRs {
    private Long id;
    private String startAt;

    public ReservationTimeRs() {}

    public ReservationTimeRs(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }
}
