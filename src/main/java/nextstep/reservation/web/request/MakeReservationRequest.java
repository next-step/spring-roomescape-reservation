package nextstep.reservation.web.request;

public class MakeReservationRequest {

    private final String date;
    private final String time;
    private final String name;

    public MakeReservationRequest(String date, String time, String name) {
        this.date = date;
        this.time = time;
        this.name = name;
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
