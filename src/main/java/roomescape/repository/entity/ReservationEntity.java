package roomescape.repository.entity;

import java.time.LocalDate;
import java.util.Objects;

public class ReservationEntity {

    private final Long id;
    private final String reservationName;
    private final LocalDate reservationDate;
    private final Long reservationTimeId;
    private final Long themeId;

    public ReservationEntity(
            Long id,
            String reservationName,
            LocalDate reservationDate,
            Long reservationTimeId,
            Long themeId
    ) {
        this.id = id;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTimeId = reservationTimeId;
        this.themeId = themeId;
    }

    public ReservationEntity changeId(Long id) {
        return new ReservationEntity(id, this.reservationName, this.reservationDate, this.reservationTimeId, themeId);
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

    public Long getThemeId() {
        return themeId;
    }
}
