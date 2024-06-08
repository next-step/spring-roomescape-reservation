package roomescape.entities;

import roomescape.reservationTime.data.ReservationTimeAddRequestDTO;

public class ReservationTime {
    private Long id;
    private String startAt;

    public ReservationTime(ReservationTimeAddRequestDTO reservationTimeAddRequestDTO) {
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

}