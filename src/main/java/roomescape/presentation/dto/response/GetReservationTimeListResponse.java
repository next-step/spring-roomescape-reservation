package roomescape.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.application.dto.result.GetReservationTimeResult;

@Builder
@Getter
@Setter
public class GetReservationTimeListResponse {
    private Long id;
    private String startAt;

    public static GetReservationTimeListResponse of(GetReservationTimeResult result){
        return GetReservationTimeListResponse.builder()
            .id(result.getId())
            .startAt(result.getStartAt())
            .build();
    }
}
