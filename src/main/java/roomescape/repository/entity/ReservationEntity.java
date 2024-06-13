package roomescape.repository.entity;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationEntity {

    private final Long id;
    private final String reservationName;
    private final LocalDate reservationDate;
    private final Long reservationTimeId;

    public ReservationEntity(Long id, String reservationName, LocalDate reservationDate, Long reservationTimeId) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
    }

    public ReservationEntity changeId(Long id) {
        return new ReservationEntity(id, this.reservationName, this.reservationDate, this.reservationTimeId);
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

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public Long getReservationTimeId() {
        return reservationTimeId;
    }
}
