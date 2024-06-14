package roomescape.admin.dto;

import roomescape.admin.entity.Reservation;
import roomescape.admin.entity.ReservationTime;
import roomescape.admin.entity.Theme;

import java.util.List;
import java.util.stream.Collectors;

public record ReadReservationResponse(Long id,
                                      String name,
                                      String date,
                                      ReservationTime time,
                                      Theme theme) {

    private ReadReservationResponse(Reservation reservation) {
        this(reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime(),
            reservation.getTheme());
    }

    public static ReadReservationResponse from(Reservation reservation){
        return new ReadReservationResponse(reservation);
    }

    public static List<ReadReservationResponse> from(List<Reservation> reservations){
        return reservations.stream()
                .map(ReadReservationResponse::new)
                .collect(Collectors.toList());
    }
}