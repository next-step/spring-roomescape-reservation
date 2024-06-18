package roomescape.presentation.dto.request;

import lombok.*;
import roomescape.application.dto.command.CreateReservationTimeCommand;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationTimeRequest {
    private String startAt;


    public CreateReservationTimeCommand toCommand() {
        return CreateReservationTimeCommand.builder()
                .startAt(startAt)
                .build();
    }
}
