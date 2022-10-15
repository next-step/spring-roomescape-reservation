package nextstep.presentation.dto;

public class ReservationRequest {

    private String date;
    private String time;
    private String name;

    private ReservationRequest() {
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getName() {
        return name;
    }
}
