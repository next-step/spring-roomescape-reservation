package roomescape.domain.reservationtime.vo;

public class ReservationTimeId {

    private final Long id;

    public ReservationTimeId(Long id) {
        this.id = id;
    }

    public static ReservationTimeId empty() {
        return new ReservationTimeId(null);
    }

    public Long getId() {
        return id;
    }
}
