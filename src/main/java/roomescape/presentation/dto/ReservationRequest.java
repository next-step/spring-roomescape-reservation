package roomescape.presentation.dto;

public class ReservationRequest {

    private final String name;
    private final String date;
    private final String time;

    private ReservationRequest(String name, String date, String time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
