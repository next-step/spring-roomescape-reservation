package roomescape.application.dto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.domain.Reservation;

@Builder
@Getter
@Setter
public class CreateReservationResult {
    private Long id;
    private String name;
    private String date;
    private CreateReservationTimeResult time;

    public static CreateReservationResult of(Reservation reservation) {
        return CreateReservationResult.builder()
                .id(reservation.getId())
                .name(reservation.getName())
                .date(reservation.getDate())
                .time(CreateReservationTimeResult.of(reservation.getTime()))
                .build();
    }
}
