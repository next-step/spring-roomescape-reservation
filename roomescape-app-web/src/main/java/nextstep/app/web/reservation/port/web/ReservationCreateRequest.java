package nextstep.app.web.reservation.port.web;

public record ReservationCreateRequest(String date,
                                       String time,
                                       String name) {
}
