package roomescape.application.service.component.remover;

import org.springframework.stereotype.Component;
import roomescape.application.service.component.validator.DeleteReservationTimeValidator;
import roomescape.domain.reservationtime.ReservationTimeRepository;
import roomescape.domain.reservationtime.vo.ReservationTimeId;

@Component
public class ReservationTimeRemover {
    private final ReservationTimeRepository repository;
    private final DeleteReservationTimeValidator validator;

    public ReservationTimeRemover(
            ReservationTimeRepository repository,
            DeleteReservationTimeValidator validator
    ) {
        this.repository = repository;
        this.validator = validator;
    }

    public void remove(ReservationTimeId id) {
        validator.validate(id);
        repository.delete(id.id());
    }
}
