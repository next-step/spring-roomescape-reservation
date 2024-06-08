package roomescape.entities;

import roomescape.reservationTime.data.ReservationTimeAddRequestDTO;

public class ReservationTime {
    private Long id;
    private String startAt;

    public ReservationTime(ReservationTimeAddRequestDTO reservationTimeAddRequestDTO) {
        if (! isValidStartAt(reservationTimeAddRequestDTO.getStartAt())){
            throw new IllegalArgumentException();
        }
        this.startAt = reservationTimeAddRequestDTO.getStartAt();

    }

    public ReservationTime(String startAt){
        this.startAt = startAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getStartAt() {
        return this.startAt;
    }

    public void setId(Long id){
        this.id = id;
    }

    private boolean isValidStartAt(String startAt){
        String[] times = startAt.split(":");
        int hour = Integer.parseInt(times[0]);
        int minute = Integer.parseInt(times[1]);
        if (hour >= 0 && hour <= 24 && minute >= 0 && minute <= 24){
            return true;
        }
        return false;
    }
}