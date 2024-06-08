package roomescape.reservation.data;

public class ReservationAddRequestDTO {
    private String name;
    private String date;
    private Long reservationTimeId;

    private String startAt;

    public ReservationAddRequestDTO() {
    }

    public ReservationAddRequestDTO(String name, String date, Long reservationTimeId, String startAt) {
        this.name = name;
        this.date = date;
        this.reservationTimeId = reservationTimeId;
        this.startAt = startAt;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public Long getReservationTimeId() {
        return this.reservationTimeId;
    }

    public String getStartAt() {
        return this.startAt;
    }
}
