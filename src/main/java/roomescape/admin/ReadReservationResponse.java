package roomescape.admin;

import java.util.List;
import java.util.stream.Collectors;

public record ReadReservationResponse(Long id,
                                      String name,
                                      String date,
                                      String time) {

    public ReadReservationResponse(Long id, Reservation reservation) {
        this(id,
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime());
    }

    public ReadReservationResponse(Reservation reservation) {
        this(reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getTime());
    }

    public static List<ReadReservationResponse> entityToList(List<Reservation> reservations){
        return reservations.stream()
                .map(ReadReservationResponse::new)
                .collect(Collectors.toList());
    }
}