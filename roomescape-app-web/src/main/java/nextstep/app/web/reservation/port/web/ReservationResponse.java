package nextstep.app.web.reservation.port.web;

import nextstep.domain.reservation.domain.model.Reservation;

import java.util.List;

public record ReservationResponse(Long id,
                                  String date,
                                  String time,
                                  String name) {
    public static List<ReservationResponse> listFrom(List<Reservation> reservations) {
        return reservations.stream()
                .map(ReservationResponse::from)
                .toList();
    }

    private static ReservationResponse from(Reservation reservation) {
        return new ReservationResponse(reservation.id(), reservation.date().toString(), reservation.time().toString(), reservation.name());
    }
}
