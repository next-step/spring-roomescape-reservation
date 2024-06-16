package roomescape.application.service.component.reader;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.NotFoundEntityException;
import roomescape.application.mapper.ReservationTimeMapper;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.repository.ReservationTimeRepository;

import static roomescape.application.error.code.ApplicationErrorCode.NOT_FOUND_ENTITY;

@Component
public class ReservationTimeReader {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeReader(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime readById(Long reservationTimeId) {
        return ReservationTimeMapper.toReservationTime(
                reservationTimeRepository.findById(reservationTimeId)
                        .orElseThrow(() -> new NotFoundEntityException(
                                NOT_FOUND_ENTITY,
                                String.format("[reservationTimeId:%d] 해당하는 엔티티를 찾을 수 없습니다.", reservationTimeId)
                        ))
        );
    }
}
