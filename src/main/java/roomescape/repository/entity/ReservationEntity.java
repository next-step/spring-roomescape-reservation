package roomescape.repository.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class ReservationEntity {

    private final Long id;
    private final String reservationName;
    private final LocalDateTime reservationDateTime;

    public ReservationEntity(Long id, String reservationName, LocalDateTime reservationDateTime) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDateTime = reservationDateTime;
    }

    public ReservationEntity changeId(Long id) {
        return new ReservationEntity(id, this.reservationName, this.reservationDateTime);
    }

    public boolean isSameId(ReservationEntity reservationEntity) {
        return Objects.nonNull(this.id) && this.id.equals(reservationEntity.id);
    }

    public boolean isSameId(Long id) {
        return Objects.nonNull(this.id) && this.id.equals(id);
    }

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public LocalDateTime getReservationDateTime() {
        return reservationDateTime;
    }
}
