package roomescape.dto.request;

public class ReservationTimeRequest {

    private String startAt;

    public ReservationTimeRequest() {
    }

    public ReservationTimeRequest(String startAt) {
        this.startAt = startAt;
    }

    public String getStartAt() {
        return startAt;
    }
}
