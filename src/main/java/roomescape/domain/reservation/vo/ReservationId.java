package roomescape.domain.reservation.vo;

public record ReservationId(Long id) {

    public ReservationId(Long id) {
        this.id = id;
    }

    public static ReservationId empty() {
        return new ReservationId(null);
    }
}
