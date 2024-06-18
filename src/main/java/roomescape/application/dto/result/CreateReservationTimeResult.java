package roomescape.application.dto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.domain.ReservationTime;

@Builder
@Getter
@Setter
public class CreateReservationTimeResult {
    private Long id;
    private String startAt;

    public static CreateReservationTimeResult of(ReservationTime reservationTime){
        return CreateReservationTimeResult.builder()
            .id(reservationTime.getId())
            .startAt(reservationTime.getStartAt())
            .build();
    }
}
