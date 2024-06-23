package roomescape.dto;

public class ReservationTimeRq {
    private String startAt;

    public ReservationTimeRq() {}

    public ReservationTimeRq(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }
}
