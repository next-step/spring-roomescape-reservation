package roomescape.presentation.dto;

public class ReservationTimeRequest {

    private final long id;
    private final String startAt;

    private ReservationTimeRequest(long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
