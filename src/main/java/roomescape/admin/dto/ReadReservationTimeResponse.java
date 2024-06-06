package roomescape.admin.dto;

import roomescape.admin.entity.ReservationTime;

import java.util.List;
import java.util.stream.Collectors;

public record ReadReservationTimeResponse(Long id,
                                          String startAt) {

    private ReadReservationTimeResponse(ReservationTime reservationTime){
        this(reservationTime.getId(), reservationTime.getStartAt());
    }

    public static ReadReservationTimeResponse entityToDTO(ReservationTime reservationTime){
        return new ReadReservationTimeResponse(reservationTime);
    }

    public static List<ReadReservationTimeResponse> entityToList(List<ReservationTime> reservations) {
        return reservations.stream()
                .map(ReadReservationTimeResponse::new)
                .collect(Collectors.toList());
    }
}