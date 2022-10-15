package nextstep.app.web.reservation.port.web;

public record ReservationResponse(Long id,
                                  String date,
                                  String time,
                                  String name) {
}
