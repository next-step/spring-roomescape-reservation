package roomescape.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.application.dto.result.CreateReservationTimeResult;

@Builder
@Getter
@Setter
public class CreateReservationTimeResponse {
    private Long id;
    private String startAt;

    public static CreateReservationTimeResponse of(CreateReservationTimeResult result){
        return CreateReservationTimeResponse.builder()
            .id(result.getId())
            .startAt(result.getStartAt())
            .build();
    }
}
