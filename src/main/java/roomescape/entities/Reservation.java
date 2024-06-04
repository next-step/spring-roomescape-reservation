package roomescape.entities;

import java.time.LocalDateTime;

public class Reservation {
    private Long id;
    private String name;
    private LocalDateTime reservedDateTime;

    public Long getId() {
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public LocalDateTime getReservedDateTime(){
        return this.reservedDateTime;
    }

}
