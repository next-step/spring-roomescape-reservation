package roomescape.reservation.data;

import lombok.Getter;
import roomescape.entities.Reservation;
import roomescape.entities.ReservationTime;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReservationResponseDto {
    private Long id;

    private String name;

    private String date;

    private String time;

    public ReservationResponseDto(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto fromEntity(Reservation reservation) {
        return new ReservationResponseDto(
          reservation.getId(),
          reservation.getName(),
          reservation.getDate(),
          reservation.getReservationTime().getStartAt());
    }

    public static List<ReservationResponseDto> toEntities(List<Reservation> reservations){
        return reservations.stream()
          .map(ReservationResponseDto::fromEntity)
          .collect(Collectors.toList());
    }
}
