package roomescape.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.application.dto.result.CreateReservationResult;

@Builder
@Getter
@Setter
public class CreateReservationResponse {
    private Long id;
    private String name;
    private String date;
    private CreateReservationTimeResponse time;

    public static CreateReservationResponse of(CreateReservationResult result){
        return CreateReservationResponse.builder()
            .id(result.getId())
            .name(result.getName())
            .date(result.getDate())
            .time(CreateReservationTimeResponse.of(result.getTime()))
            .build();
    }
}
