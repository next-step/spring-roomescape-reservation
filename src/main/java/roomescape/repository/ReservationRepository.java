package roomescape.repository;

import roomescape.repository.entity.ReservationEntity;
import roomescape.repository.projection.ReservationViewProjection;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    ReservationEntity save(ReservationEntity reservationEntity);

    List<ReservationEntity> findAll();

    void delete(Long reservationId);

    Optional<ReservationEntity> findById(Long reservationId);

    List<ReservationViewProjection> findAllReservationViewProjection();
}
