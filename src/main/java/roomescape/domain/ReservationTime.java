package roomescape.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ReservationTime {
    private Long id;
    private String startAt;

    public ReservationTime(Long id, String startAt) {
        this.id = id;
        this.startAt = startAt;
    }

    public static ReservationTime create(String startAt) {
        return new ReservationTime(null, startAt);
    }
}
