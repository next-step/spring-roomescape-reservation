package roomescape.dto.response;

public class ReservationTimeResponse {

    private Long id;
    private String startAt;

    public ReservationTimeResponse() {
    }

    public ReservationTimeResponse(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public Long getId() {
        return id;
    }

    public String getStartAt() {
        return startAt;
    }
}
