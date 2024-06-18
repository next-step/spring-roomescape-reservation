package roomescape.presentation.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetReservationListResponse {
    private Long id;
    private String name;
    private String date;
    private GetReservationTimeListResponse time;
}
