package roomescape.domain.reservation.vo;

public record ReservationId(Long id) {

    public static ReservationId empty() {
        return new ReservationId(null);
    }
}
