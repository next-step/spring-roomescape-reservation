package roomescape.application.service.component.reader;

import org.springframework.stereotype.Component;
import roomescape.application.error.exception.NotFoundEntityException;
import roomescape.application.mapper.ReservationTimeMapper;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.repository.ReservationTimeRepository;

@Component
public class ReservationTimeReader {

    private final ReservationTimeRepository reservationTimeRepository;

    public ReservationTimeReader(ReservationTimeRepository reservationTimeRepository) {
        this.reservationTimeRepository = reservationTimeRepository;
    }

    public ReservationTime readById(ReservationTimeId timeId) {
        return ReservationTimeMapper.toReservationTime(
                reservationTimeRepository.findById(timeId.id())
                        .orElseThrow(() -> NotFoundEntityException.reservationTime(timeId))
        );
    }
}
