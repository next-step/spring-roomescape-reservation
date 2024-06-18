package roomescape.application.dto.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CreateReservationCommand {
    private String name;
    private String date;
    private String timeId;
}
