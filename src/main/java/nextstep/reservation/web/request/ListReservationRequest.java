package nextstep.reservation.web.request;

public class ListReservationRequest {

    private final String date;

    public ListReservationRequest(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
