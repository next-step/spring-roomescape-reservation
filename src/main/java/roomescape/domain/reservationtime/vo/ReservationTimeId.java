package roomescape.domain.reservationtime.vo;

public record ReservationTimeId(Long id) {

    public static ReservationTimeId empty() {
        return new ReservationTimeId(null);
    }
}
