package roomescape.application.service.component.reader;

import org.springframework.stereotype.Component;
import roomescape.domain.reservationtime.ReservationTime;
import roomescape.domain.reservationtime.ReservationTimeRepository;
import roomescape.domain.reservationtime.vo.ReservationTimeId;
import roomescape.error.exception.NotFoundDomainException;

@Component
public class ReservationTimeReader {

    private final ReservationTimeRepository repository;

    public ReservationTimeReader(ReservationTimeRepository repository) {
        this.repository = repository;
    }

    public ReservationTime readById(ReservationTimeId id) {
        return repository.findById(id.id())
                .orElseThrow(() -> NotFoundDomainException.notFoundReservationTime(id.id()));
    }
}
