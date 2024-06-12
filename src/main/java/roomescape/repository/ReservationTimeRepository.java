package roomescape.repository;

import roomescape.repository.entity.ReservationTimeEntity;

import java.util.Optional;

public interface ReservationTimeRepository {

    ReservationTimeEntity save(ReservationTimeEntity reservationTimeEntity);

    Optional<ReservationTimeEntity> findById(Long id);
}
