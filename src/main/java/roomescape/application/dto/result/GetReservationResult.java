package roomescape.application.dto.result;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GetReservationResult {
    private Long id;
    private String name;
    private String date;
    private String time;
}
