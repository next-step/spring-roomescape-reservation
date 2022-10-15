package nextstep.reservation.web.request;

public class CancelReservationRequest {

    private final String date;
    private final String time;

    public CancelReservationRequest(String date, String time) {
        this.date = date;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}


