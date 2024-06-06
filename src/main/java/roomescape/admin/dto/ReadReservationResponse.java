package roomescape.admin.dto;

import roomescape.admin.entity.Reservation;
import roomescape.admin.entity.ReservationTime;

import java.util.List;
import java.util.stream.Collectors;

public record ReadReservationResponse(Long id,
                                      String name,
                                      String date,
                                      ReservationTime time) {

    private ReadReservationResponse(Reservation reservation) {
        this(reservation.getId(),
            reservation.getName(),
            reservation.getDate(),
            reservation.getTime());
    }

    public static ReadReservationResponse entityToDTO(Reservation reservation){
        return new ReadReservationResponse(reservation);
    }

    public static List<ReadReservationResponse> entityToList(List<Reservation> reservations){
        return reservations.stream()
                .map(ReadReservationResponse::new)
                .collect(Collectors.toList());
    }
}