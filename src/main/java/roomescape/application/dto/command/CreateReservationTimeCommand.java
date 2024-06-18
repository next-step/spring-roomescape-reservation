package roomescape.application.dto.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateReservationTimeCommand {
    private String startAt;
}
