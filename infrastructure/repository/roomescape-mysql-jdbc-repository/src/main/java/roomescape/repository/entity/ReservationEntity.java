package roomescape.repository.entity;

import roomescape.domain.reservation.Reservation;
import roomescape.domain.reservation.vo.ReservationDate;
import roomescape.domain.reservation.vo.ReservationId;
import roomescape.domain.reservation.vo.ReservationName;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.domain.theme.vo.ThemeId;

import java.time.LocalDate;

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

    public static ReservationEntity from(Reservation reservation) {
        return new ReservationEntity(
                reservation.getId(),
                reservation.getName(),
                reservation.getDate(),
                reservation.getReservationTimeId(),
                reservation.getThemeId()
        );
    }

    public ReservationEntity withId(Long id) {
        return new ReservationEntity(id, this.reservationName, this.reservationDate, this.reservationTimeId, themeId);
    }

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return this.reservationName;
    }

    public LocalDate getReservationDate() {
        return this.reservationDate;
    }

    public Long getReservationTimeId() {
        return this.reservationTimeId;
    }

    public Long getThemeId() {
        return this.themeId;
    }

    public Reservation toDomain() {
        return new Reservation(
                new ReservationId(this.id),
                new ReservationName(this.reservationName),
                new ReservationDate(this.reservationDate),
                new ReservationTimeId(this.reservationTimeId),
                new ThemeId(this.themeId)
        );
    }
}
