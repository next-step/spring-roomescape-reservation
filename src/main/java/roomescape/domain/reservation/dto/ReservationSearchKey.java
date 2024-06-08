package roomescape.domain.reservation.dto;

import lombok.Builder;
import lombok.Getter;
import roomescape.domain.reservation.domain.model.ReservationGuestName;
import roomescape.domain.reservation.domain.model.ReservationTimeStamp;
import roomescape.domain.reservation.service.request.ReserveRequest;

@Getter
public class ReservationSearchKey {

    private final ReservationGuestName name;
    private final ReservationTimeStamp timeStamp;

    @Builder
    private ReservationSearchKey(final ReservationGuestName name, final ReservationTimeStamp timeStamp) {
        this.name = name;
        this.timeStamp = timeStamp;
    }

    public static ReservationSearchKey from(ReserveRequest request) {
        return ReservationSearchKey.builder()
                .name(new ReservationGuestName(request.getName()))
                .timeStamp(ReservationTimeStamp.of(request.getDate(), request.getTime()))
                .build();
    }

    @Override
    public String toString() {
        return "ReservationSearchKey{" +
                "name=" + name +
                ", timeStamp=" + timeStamp +
                '}';
    }

}
