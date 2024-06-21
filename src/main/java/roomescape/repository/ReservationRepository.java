package roomescape.repository;

import roomescape.repository.entity.ReservationEntity;
import roomescape.repository.projection.ReservationViewProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    ReservationEntity save(ReservationEntity reservationEntity);

    List<ReservationEntity> findAll();

    void delete(Long reservationId);

    Optional<ReservationEntity> findById(Long reservationId);

    List<ReservationViewProjection> findAllReservationViewProjection();

    Optional<ReservationEntity> findByTimeId(Long timeId);

    Optional<ReservationEntity> findByDateAndTimeId(LocalDate date, Long timeId);
}
