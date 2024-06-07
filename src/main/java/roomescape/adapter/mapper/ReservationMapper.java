package roomescape.adapter.mapper;

import roomescape.adapter.in.web.dto.ReservationCommand;
import roomescape.adapter.in.web.dto.ReservationResponse;
import roomescape.adapter.out.ReservationEntity;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

public class ReservationMapper {

  private ReservationMapper() {
    throw new UnsupportedOperationException("생성 불가");
  }

  public static ReservationResponse mapToResponse(Reservation reservation) {
    return ReservationResponse.of(reservation.getId(), reservation.getName(), reservation.getDate(),
      reservation.getTime()
                 .getStartAt());
  }

  public static Reservation mapToDomain(ReservationCommand reservationCommand) {
    return Reservation.of(reservationCommand.name(), reservationCommand.date(), ReservationTime.of(
      reservationCommand.time()));
  }

  public static Reservation mapToDomain(ReservationEntity reservationEntity) {
    return Reservation.of(reservationEntity.getId(), reservationEntity.getName(), reservationEntity.getDate(),
      ReservationTimeMapper.mapToDomain(reservationEntity.getTime()));
  }

  public static ReservationEntity mapToEntity(Reservation reservation) {
    return ReservationEntity.of(reservation.getId(), reservation.getName(), reservation.getDate(),
      ReservationTimeMapper.mapToEntity(reservation.getTime()));
  }
}
