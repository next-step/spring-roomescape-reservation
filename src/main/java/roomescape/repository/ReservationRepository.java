package roomescape.repository;

import roomescape.repository.entity.ReservationEntity;

import java.util.List;

public interface ReservationRepository {

    ReservationEntity save(ReservationEntity reservationEntity);

    List<ReservationEntity> findAll();
}
