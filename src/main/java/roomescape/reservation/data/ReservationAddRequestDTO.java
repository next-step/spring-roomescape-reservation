package roomescape.reservation.data;

import lombok.Getter;
import roomescape.entities.Reservation;
import roomescape.entities.ReservationTime;
import roomescape.entities.Theme;

@Getter
public class ReservationAddRequestDto {
    private String name;

    private String date;

    private Long timeId;

    private Long themeId;

    public ReservationAddRequestDto() {
    }

    public ReservationAddRequestDto(String name,
                                    String date,
                                    Long timeId,
                                    Long themeId) {
        this.name = name;
        this.date = date;
        this.timeId = timeId;
        this.themeId = themeId;
    }

    public Reservation toEntity(){
        return new Reservation(
          this.name,
          this.date,
          new ReservationTime(this.timeId, ""),
          new Theme(this.themeId, "", "", "")
      );
    }
}
