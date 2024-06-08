package roomescape.adapter.mapper;


import roomescape.adapter.in.web.dto.ReservationTimeCommand;
import roomescape.adapter.in.web.dto.ReservationTimeResponse;
import roomescape.adapter.out.ReservationTimeEntity;
import roomescape.domain.ReservationTime;

public class ReservationTimeMapper {

  private ReservationTimeMapper() {
    throw new UnsupportedOperationException("생성 불가");
  }

  public static ReservationTime mapToDomain(ReservationTimeCommand reservationTimeCommand) {
    return ReservationTime.of(null, reservationTimeCommand.startAt());
  }

  public static ReservationTime mapToDomain(ReservationTimeEntity reservationTimeEntity) {
    return ReservationTime.of(reservationTimeEntity.getId(), reservationTimeEntity.getStartAt());
  }

  public static ReservationTimeResponse mapToResponse(ReservationTime reservationTime) {
    return ReservationTimeResponse.of(reservationTime.getId(), reservationTime.getStartAt());
  }

  public static ReservationTimeEntity mapToEntity(ReservationTime reservationTime) {
    return ReservationTimeEntity.of(reservationTime.getId(), reservationTime.getStartAt());
  }
}
