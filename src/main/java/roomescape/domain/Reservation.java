package roomescape.domain;

import lombok.Getter;
import lombok.Setter;
import roomescape.application.dto.command.CreateReservationCommand;

@Getter
@Setter
public class Reservation {

    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time
    ) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public static Reservation create(CreateReservationCommand command) {
        return new Reservation(null, command.getName(), command.getDate(), command.getTime());
    }
}
