package roomescape.presentation.dto;

public class ReservationRequest {

    private final String name;
    private final String date;
    private final long timeId;
    private final String startAt;

    private ReservationRequest(String name, String date, long timeId, String startAt) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.startAt = startAt;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public long getTimeId() {
        return timeId;
    }

    public String getStartAt() {
        return startAt;
    }
}
