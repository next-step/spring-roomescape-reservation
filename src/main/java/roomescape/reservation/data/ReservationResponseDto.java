package roomescape.reservation.data;

import lombok.Getter;
import roomescape.entities.Reservation;
import roomescape.entities.ReservationTime;
import roomescape.entities.Theme;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReservationResponseDto {
    private Long id;

    private String name;

    private Theme theme;

    private String date;

    private ReservationTime time;

    public ReservationResponseDto(Long id, String name, Theme theme, String date, ReservationTime time) {
        this.id = id;
        this.name = name;
        this.theme = theme;
        this.date = date;
        this.time = time;
    }

    public static ReservationResponseDto fromEntity(Reservation reservation) {
        return new ReservationResponseDto(
          reservation.getId(),
          reservation.getName(),
          reservation.getTheme(),
          reservation.getDate(),
          reservation.getReservationTime());
    }

    public static List<ReservationResponseDto> toEntities(List<Reservation> reservations){
        return reservations.stream()
          .map(ReservationResponseDto::fromEntity)
          .collect(Collectors.toList());
    }
}
