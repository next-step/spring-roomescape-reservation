package roomescape.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import roomescape.application.dto.command.CreateReservationCommand;

@Builder
@Setter
@Getter
public class CreateReservationRequest {
    private String name;
    private String date;
    private String time;

    public CreateReservationCommand toCommand() {
        return CreateReservationCommand.builder()
            .name(name)
            .date(date)
            .time(time)
            .build();
    }
}
