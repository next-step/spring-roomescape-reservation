package roomescape.application.dto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetReservationTimeResult {
    private Long id;
    private String startAt;
}
