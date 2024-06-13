package roomescape.reservation;

public class NewReservation {
    private final String name;
    private final ReservationDate date;
    private final Long timeId;

    public NewReservation(String name, ReservationDate date, Long timeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
    }

    public ReservationEntity toEntity() {
        return new ReservationEntity(name, date.toString(), timeId);
    }
}
